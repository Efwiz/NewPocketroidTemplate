package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CompilationExceptionKt;
import org.jetbrains.kotlin.backend.common.Mapping;
import org.jetbrains.kotlin.backend.common.MappingsKt;
import org.jetbrains.kotlin.backend.common.lower.InnerClassesSupport;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.backend.js.JsMapping;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.backend.js.utils.Namer;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFieldBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrValueParameterBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.utils.MemoryOptimizedCollectionUtilKt;

/* JADX INFO: compiled from: JsInnerClassesSupport.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u000e\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\tH\u0002J\u0010\u0010\u0010\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\tH\u0016J\u0012\u0010\u0012\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0013\u001a\u00020\u000bH\u0016J\u0010\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u000bH\u0016R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\r0\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/JsInnerClassesSupport;", "Lorg/jetbrains/kotlin/backend/common/lower/InnerClassesSupport;", "mapping", "Lorg/jetbrains/kotlin/ir/backend/js/JsMapping;", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsMapping;Lorg/jetbrains/kotlin/ir/declarations/IrFactory;)V", "innerClassConstructors", "Lorg/jetbrains/kotlin/backend/common/Mapping$Delegate;", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "originalInnerClassPrimaryConstructorByClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "outerThisFieldSymbols", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "createInnerClassConstructorWithOuterThisParameter", "oldConstructor", "getInnerClassConstructorWithOuterThisParameter", "innerClassConstructor", "getInnerClassOriginalPrimaryConstructorOrNull", "innerClass", "getOuterThisField", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsInnerClassesSupport implements InnerClassesSupport {
    private final Mapping.Delegate<IrConstructor, IrConstructor> innerClassConstructors;
    private final IrFactory irFactory;
    private final Mapping.Delegate<IrClass, IrConstructor> originalInnerClassPrimaryConstructorByClass;
    private final Mapping.Delegate<IrClass, IrField> outerThisFieldSymbols;

    public JsInnerClassesSupport(JsMapping mapping, IrFactory irFactory) {
        Intrinsics.checkNotNullParameter(mapping, "mapping");
        Intrinsics.checkNotNullParameter(irFactory, "irFactory");
        this.irFactory = irFactory;
        this.outerThisFieldSymbols = mapping.getOuterThisFieldSymbols();
        this.innerClassConstructors = mapping.getInnerClassConstructors();
        this.originalInnerClassPrimaryConstructorByClass = mapping.getOriginalInnerClassPrimaryConstructorByClass();
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.InnerClassesSupport
    public IrField getOuterThisField(final IrClass innerClass) {
        Intrinsics.checkNotNullParameter(innerClass, "innerClass");
        if (!innerClass.getIsInner()) {
            CompilationExceptionKt.compilationException("Class is not inner", (IrDeclaration) innerClass);
            throw null;
        }
        return (IrField) MappingsKt.getOrPut(this.outerThisFieldSymbols, innerClass, new Function0<IrField>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.JsInnerClassesSupport.getOuterThisField.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrField invoke() {
                IrDeclarationParent parent = innerClass.getParent();
                IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
                if (irClass != null) {
                    IrFactory irFactory = this.irFactory;
                    IrFieldBuilder irFieldBuilder = new IrFieldBuilder();
                    irFieldBuilder.setOrigin(IrDeclarationOrigin.FIELD_FOR_OUTER_THIS.INSTANCE);
                    Name nameIdentifier = Name.identifier(Namer.INSTANCE.getSYNTHETIC_RECEIVER_NAME());
                    Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                    irFieldBuilder.setName(nameIdentifier);
                    irFieldBuilder.setType(IrUtilsKt.getDefaultType(irClass));
                    DescriptorVisibility PROTECTED = DescriptorVisibilities.PROTECTED;
                    Intrinsics.checkNotNullExpressionValue(PROTECTED, "PROTECTED");
                    irFieldBuilder.setVisibility(PROTECTED);
                    irFieldBuilder.setFinal(true);
                    irFieldBuilder.setExternal(false);
                    irFieldBuilder.setStatic(false);
                    IrField irFieldBuildField = DeclarationBuildersKt.buildField(irFactory, irFieldBuilder);
                    irFieldBuildField.setParent(innerClass);
                    return irFieldBuildField;
                }
                CompilationExceptionKt.compilationException("No containing class for inner class", (IrDeclaration) innerClass);
                throw null;
            }
        });
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.InnerClassesSupport
    public IrConstructor getInnerClassConstructorWithOuterThisParameter(final IrConstructor innerClassConstructor) {
        Intrinsics.checkNotNullParameter(innerClassConstructor, "innerClassConstructor");
        IrDeclarationParent parent = innerClassConstructor.getParent();
        Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
        IrClass irClass = (IrClass) parent;
        irClass.getIsInner();
        IrConstructor irConstructor = (IrConstructor) MappingsKt.getOrPut(this.innerClassConstructors, innerClassConstructor, new Function0<IrConstructor>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.JsInnerClassesSupport.getInnerClassConstructorWithOuterThisParameter.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrConstructor invoke() {
                return JsInnerClassesSupport.this.createInnerClassConstructorWithOuterThisParameter(innerClassConstructor);
            }
        });
        if (innerClassConstructor.getIsPrimary()) {
            this.originalInnerClassPrimaryConstructorByClass.set(irClass, innerClassConstructor);
        }
        return irConstructor;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.InnerClassesSupport
    public IrConstructor getInnerClassOriginalPrimaryConstructorOrNull(IrClass innerClass) {
        Intrinsics.checkNotNullParameter(innerClass, "innerClass");
        innerClass.getIsInner();
        return this.originalInnerClassPrimaryConstructorByClass.get(innerClass);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrConstructor createInnerClassConstructorWithOuterThisParameter(IrConstructor oldConstructor) {
        IrDeclarationParent parent = oldConstructor.getParent();
        Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
        IrDeclarationParent parent2 = ((IrClass) parent).getParent();
        Intrinsics.checkNotNull(parent2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
        IrSimpleType defaultType = IrUtilsKt.getDefaultType((IrClass) parent2);
        IrFactory irFactory = this.irFactory;
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        irFunctionBuilder.updateFrom((IrFunction) oldConstructor);
        irFunctionBuilder.setReturnType(oldConstructor.getReturnType());
        IrConstructor irConstructorBuildConstructor = DeclarationBuildersKt.buildConstructor(irFactory, irFunctionBuilder);
        irConstructorBuildConstructor.setParent(oldConstructor.getParent());
        irConstructorBuildConstructor.setAnnotations(oldConstructor.getAnnotations());
        IrUtilsKt.copyTypeParametersFrom$default(irConstructorBuildConstructor, oldConstructor, null, null, 6, null);
        IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
        irValueParameterBuilder.setOrigin(JsIrBuilder.SYNTHESIZED_DECLARATION.INSTANCE);
        Name nameIdentifier = Name.identifier(Namer.INSTANCE.getOUTER_NAME());
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irValueParameterBuilder.setName(nameIdentifier);
        irValueParameterBuilder.setIndex(0);
        irValueParameterBuilder.setType(defaultType);
        Unit unit = Unit.INSTANCE;
        IrConstructor irConstructor = irConstructorBuildConstructor;
        List listMutableListOf = CollectionsKt.mutableListOf(DeclarationBuildersKt.buildValueParameter(irConstructor.getFactory(), irValueParameterBuilder, irConstructor));
        for (IrValueParameter irValueParameter : oldConstructor.getValueParameters()) {
            listMutableListOf.add(IrUtilsKt.copyTo$default(irValueParameter, irConstructorBuildConstructor, null, irValueParameter.getIndex() + 1, 0, 0, null, null, null, null, null, false, false, false, 8186, null));
        }
        irConstructorBuildConstructor.setValueParameters(MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus((List) irConstructorBuildConstructor.getValueParameters(), listMutableListOf));
        return irConstructorBuildConstructor;
    }
}
