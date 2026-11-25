package org.jetbrains.kotlin.backend.jvm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.lower.InnerClassesSupport;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFieldBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrValueParameterBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.load.java.JavaDescriptorVisibilities;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: JvmInnerClassesSupport.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u0007H\u0002J\u0010\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u0007H\u0016J\u0012\u0010\u0010\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0011\u001a\u00020\tH\u0016J\u0010\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\tH\u0016R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u000b0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmInnerClassesSupport;", "Lorg/jetbrains/kotlin/backend/common/lower/InnerClassesSupport;", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "(Lorg/jetbrains/kotlin/ir/declarations/IrFactory;)V", "innerClassConstructors", "Ljava/util/concurrent/ConcurrentHashMap;", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "originalInnerClassPrimaryConstructorByClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "outerThisDeclarations", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "createInnerClassConstructorWithOuterThisParameter", "oldConstructor", "getInnerClassConstructorWithOuterThisParameter", "innerClassConstructor", "getInnerClassOriginalPrimaryConstructorOrNull", "innerClass", "getOuterThisField", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmInnerClassesSupport implements InnerClassesSupport {
    private final ConcurrentHashMap<IrConstructor, IrConstructor> innerClassConstructors;
    private final IrFactory irFactory;
    private final ConcurrentHashMap<IrClass, IrConstructor> originalInnerClassPrimaryConstructorByClass;
    private final ConcurrentHashMap<IrClass, IrField> outerThisDeclarations;

    public JvmInnerClassesSupport(IrFactory irFactory) {
        Intrinsics.checkNotNullParameter(irFactory, "irFactory");
        this.irFactory = irFactory;
        this.outerThisDeclarations = new ConcurrentHashMap<>();
        this.innerClassConstructors = new ConcurrentHashMap<>();
        this.originalInnerClassPrimaryConstructorByClass = new ConcurrentHashMap<>();
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.InnerClassesSupport
    public IrField getOuterThisField(IrClass innerClass) {
        Intrinsics.checkNotNullParameter(innerClass, "innerClass");
        ConcurrentHashMap<IrClass, IrField> concurrentHashMap = this.outerThisDeclarations;
        IrField irFieldBuildField = concurrentHashMap.get(innerClass);
        if (irFieldBuildField == null) {
            innerClass.getIsInner();
            IrFactory irFactory = this.irFactory;
            IrFieldBuilder irFieldBuilder = new IrFieldBuilder();
            Name nameIdentifier = Name.identifier(AsmUtil.CAPTURED_THIS_FIELD);
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            irFieldBuilder.setName(nameIdentifier);
            irFieldBuilder.setType(IrUtilsKt.getDefaultType(IrUtilsKt.getParentAsClass(innerClass)));
            irFieldBuilder.setOrigin(IrDeclarationOrigin.FIELD_FOR_OUTER_THIS.INSTANCE);
            DescriptorVisibility PACKAGE_VISIBILITY = JavaDescriptorVisibilities.PACKAGE_VISIBILITY;
            Intrinsics.checkNotNullExpressionValue(PACKAGE_VISIBILITY, "PACKAGE_VISIBILITY");
            irFieldBuilder.setVisibility(PACKAGE_VISIBILITY);
            irFieldBuilder.setFinal(true);
            irFieldBuildField = DeclarationBuildersKt.buildField(irFactory, irFieldBuilder);
            irFieldBuildField.setParent(innerClass);
            IrField irFieldPutIfAbsent = concurrentHashMap.putIfAbsent(innerClass, irFieldBuildField);
            if (irFieldPutIfAbsent != null) {
                irFieldBuildField = irFieldPutIfAbsent;
            }
        }
        Intrinsics.checkNotNullExpressionValue(irFieldBuildField, "getOrPut(...)");
        return irFieldBuildField;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.InnerClassesSupport
    public IrConstructor getInnerClassConstructorWithOuterThisParameter(IrConstructor innerClassConstructor) {
        IrConstructor irConstructorPutIfAbsent;
        Intrinsics.checkNotNullParameter(innerClassConstructor, "innerClassConstructor");
        IrDeclarationParent parent = innerClassConstructor.getParent();
        Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
        IrClass irClass = (IrClass) parent;
        irClass.getIsInner();
        ConcurrentHashMap<IrConstructor, IrConstructor> concurrentHashMap = this.innerClassConstructors;
        IrConstructor irConstructorCreateInnerClassConstructorWithOuterThisParameter = concurrentHashMap.get(innerClassConstructor);
        if (irConstructorCreateInnerClassConstructorWithOuterThisParameter == null && (irConstructorPutIfAbsent = concurrentHashMap.putIfAbsent(innerClassConstructor, (irConstructorCreateInnerClassConstructorWithOuterThisParameter = createInnerClassConstructorWithOuterThisParameter(innerClassConstructor)))) != null) {
            irConstructorCreateInnerClassConstructorWithOuterThisParameter = irConstructorPutIfAbsent;
        }
        IrConstructor irConstructor = irConstructorCreateInnerClassConstructorWithOuterThisParameter;
        if (innerClassConstructor.getIsPrimary()) {
            this.originalInnerClassPrimaryConstructorByClass.put(irClass, innerClassConstructor);
        }
        Intrinsics.checkNotNullExpressionValue(irConstructorCreateInnerClassConstructorWithOuterThisParameter, "also(...)");
        return irConstructor;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.InnerClassesSupport
    public IrConstructor getInnerClassOriginalPrimaryConstructorOrNull(IrClass innerClass) {
        Intrinsics.checkNotNullParameter(innerClass, "innerClass");
        innerClass.getIsInner();
        return this.originalInnerClassPrimaryConstructorByClass.get(innerClass);
    }

    private final IrConstructor createInnerClassConstructorWithOuterThisParameter(IrConstructor oldConstructor) {
        IrFactory irFactory = this.irFactory;
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        irFunctionBuilder.updateFrom((IrFunction) oldConstructor);
        irFunctionBuilder.setReturnType(oldConstructor.getReturnType());
        IrConstructor irConstructorBuildConstructor = DeclarationBuildersKt.buildConstructor(irFactory, irFunctionBuilder);
        irConstructorBuildConstructor.setParent(oldConstructor.getParent());
        irConstructorBuildConstructor.setReturnType(oldConstructor.getReturnType());
        IrUtilsKt.copyAnnotationsFrom(irConstructorBuildConstructor, oldConstructor);
        IrUtilsKt.copyTypeParametersFrom$default(irConstructorBuildConstructor, oldConstructor, null, null, 6, null);
        IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
        irValueParameterBuilder.setOrigin(JvmLoweredDeclarationOrigin.FIELD_FOR_OUTER_THIS.INSTANCE);
        Name nameIdentifier = Name.identifier(AsmUtil.CAPTURED_THIS_FIELD);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irValueParameterBuilder.setName(nameIdentifier);
        irValueParameterBuilder.setIndex(0);
        irValueParameterBuilder.setType(IrUtilsKt.getDefaultType(IrUtilsKt.getParentAsClass(IrUtilsKt.getParentAsClass(oldConstructor))));
        IrConstructor irConstructor = irConstructorBuildConstructor;
        List listListOf = CollectionsKt.listOf(DeclarationBuildersKt.buildValueParameter(irConstructor.getFactory(), irValueParameterBuilder, irConstructor));
        List<IrValueParameter> valueParameters = oldConstructor.getValueParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
        for (IrValueParameter irValueParameter : valueParameters) {
            arrayList.add(IrUtilsKt.copyTo$default(irValueParameter, irConstructorBuildConstructor, null, irValueParameter.getIndex() + 1, 0, 0, null, null, null, null, null, false, false, false, 8186, null));
        }
        irConstructorBuildConstructor.setValueParameters(CollectionsKt.plus((Collection) listListOf, (Iterable) arrayList));
        irConstructorBuildConstructor.setMetadata(oldConstructor.getMetadata());
        return irConstructorBuildConstructor;
    }
}
