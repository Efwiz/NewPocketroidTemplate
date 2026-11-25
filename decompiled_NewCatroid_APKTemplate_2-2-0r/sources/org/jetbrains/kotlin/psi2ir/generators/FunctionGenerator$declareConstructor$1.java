package org.jetbrains.kotlin.psi2ir.generators;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.declarations.DescriptorMetadataSource;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.types.impl.IrUninitializedType;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.psi.KtPureElement;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;

/* JADX INFO: compiled from: FunctionGenerator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {SpecialNames.ANONYMOUS_STRING, "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "it", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
public final class FunctionGenerator$declareConstructor$1 extends Lambda implements Function1<IrConstructorSymbol, IrConstructor> {
    final /* synthetic */ ClassConstructorDescriptor $constructorDescriptor;
    final /* synthetic */ int $endOffset;
    final /* synthetic */ List<KtPureElement> $ktContextReceiversElements;
    final /* synthetic */ IrDeclarationOrigin.DEFINED $origin;
    final /* synthetic */ int $startOffset;
    final /* synthetic */ FunctionGenerator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public FunctionGenerator$declareConstructor$1(ClassConstructorDescriptor classConstructorDescriptor, FunctionGenerator functionGenerator, int i, int i2, IrDeclarationOrigin.DEFINED defined, List<? extends KtPureElement> list) {
        super(1);
        this.$constructorDescriptor = classConstructorDescriptor;
        this.this$0 = functionGenerator;
        this.$startOffset = i;
        this.$endOffset = i2;
        this.$origin = defined;
        this.$ktContextReceiversElements = list;
    }

    @Override // kotlin.jvm.functions.Function1
    public final IrConstructor invoke(IrConstructorSymbol it) {
        Intrinsics.checkNotNullParameter(it, "it");
        ClassConstructorDescriptor classConstructorDescriptor = this.$constructorDescriptor;
        FunctionGenerator functionGenerator = this.this$0;
        int i = this.$startOffset;
        int i2 = this.$endOffset;
        IrDeclarationOrigin.DEFINED defined = this.$origin;
        Name nameNameForDeclaration = functionGenerator.getContext().getSymbolTable().getNameProvider().nameForDeclaration(classConstructorDescriptor);
        DescriptorVisibility visibility = classConstructorDescriptor.getVisibilityImpl();
        Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
        IrConstructor irConstructorCreateConstructor$default = IrFactory.DefaultImpls.createConstructor$default(functionGenerator.getContext().getIrFactory(), i, i2, defined, nameNameForDeclaration, visibility, classConstructorDescriptor.isInline(), classConstructorDescriptor.get$isExpect(), IrUninitializedType.INSTANCE, it, classConstructorDescriptor.isPrimary(), DescriptorUtilsKt.isEffectivelyExternal(classConstructorDescriptor), (DeserializedContainerSource) null, 2048, (Object) null);
        List<KtPureElement> list = this.$ktContextReceiversElements;
        irConstructorCreateConstructor$default.setMetadata(new DescriptorMetadataSource.Function(it.getDescriptor()));
        irConstructorCreateConstructor$default.setContextReceiverParametersCount(list.size());
        return irConstructorCreateConstructor$default;
    }
}
