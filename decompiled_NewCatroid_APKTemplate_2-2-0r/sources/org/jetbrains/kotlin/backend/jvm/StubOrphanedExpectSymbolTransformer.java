package org.jetbrains.kotlin.backend.jvm;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.ir.ExpectSymbolTransformer;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.util.DeclarationStubGenerator;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.multiplatform.ExpectedActualResolverKt;

/* JADX INFO: compiled from: OrphanedExpectUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0014\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0015\u001a\u00020\tH\u0014J\u0012\u0010\u0016\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0015\u001a\u00020\fH\u0014J\u0012\u0010\u0017\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0015\u001a\u00020\u000fH\u0014J\u0012\u0010\u0018\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0015\u001a\u00020\u0012H\u0014J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0014J\u0014\u0010\u001d\u001a\u00020\u001e*\u00020\u001c2\u0006\u0010\u0015\u001a\u00020\u001fH\u0002J\f\u0010 \u001a\u00020\u001a*\u00020\u001fH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00130\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/StubOrphanedExpectSymbolTransformer;", "Lorg/jetbrains/kotlin/backend/common/ir/ExpectSymbolTransformer;", "stubGenerator", "Lorg/jetbrains/kotlin/ir/util/DeclarationStubGenerator;", "(Lorg/jetbrains/kotlin/ir/util/DeclarationStubGenerator;)V", "getStubGenerator", "()Lorg/jetbrains/kotlin/ir/util/DeclarationStubGenerator;", "stubbedClasses", "", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "stubbedConstructors", "Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "stubbedFunctions", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "stubbedProperties", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "Lorg/jetbrains/kotlin/backend/common/ir/ExpectSymbolTransformer$ActualPropertyResult;", "getActualClass", "descriptor", "getActualConstructor", "getActualFunction", "getActualProperty", "isTargetDeclaration", "", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "ensureClassParent", "", "Lorg/jetbrains/kotlin/descriptors/MemberDescriptor;", "isOrphanedExpect", "backend.jvm.entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class StubOrphanedExpectSymbolTransformer extends ExpectSymbolTransformer {
    private final DeclarationStubGenerator stubGenerator;
    private final Map<ClassDescriptor, IrClassSymbol> stubbedClasses;
    private final Map<ClassConstructorDescriptor, IrConstructorSymbol> stubbedConstructors;
    private final Map<FunctionDescriptor, IrSimpleFunctionSymbol> stubbedFunctions;
    private final Map<PropertyDescriptor, ExpectSymbolTransformer.ActualPropertyResult> stubbedProperties;

    public StubOrphanedExpectSymbolTransformer(DeclarationStubGenerator stubGenerator) {
        Intrinsics.checkNotNullParameter(stubGenerator, "stubGenerator");
        this.stubGenerator = stubGenerator;
        this.stubbedClasses = new LinkedHashMap();
        this.stubbedProperties = new LinkedHashMap();
        this.stubbedConstructors = new LinkedHashMap();
        this.stubbedFunctions = new LinkedHashMap();
    }

    public final DeclarationStubGenerator getStubGenerator() {
        return this.stubGenerator;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.ExpectSymbolTransformer
    protected IrClassSymbol getActualClass(ClassDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (!isOrphanedExpect(descriptor)) {
            return null;
        }
        Map<ClassDescriptor, IrClassSymbol> map = this.stubbedClasses;
        IrClassSymbol symbol = map.get(descriptor);
        if (symbol == null) {
            symbol = this.stubGenerator.generateClassStub(new FakeActualClassDescriptor(descriptor)).getSymbol();
            map.put(descriptor, symbol);
        }
        return symbol;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.ExpectSymbolTransformer
    protected ExpectSymbolTransformer.ActualPropertyResult getActualProperty(PropertyDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        PropertyDescriptor propertyDescriptor = descriptor;
        if (!isOrphanedExpect(propertyDescriptor)) {
            return null;
        }
        Map<PropertyDescriptor, ExpectSymbolTransformer.ActualPropertyResult> map = this.stubbedProperties;
        ExpectSymbolTransformer.ActualPropertyResult actualPropertyResult = map.get(descriptor);
        if (actualPropertyResult == null) {
            IrProperty irPropertyGeneratePropertyStub = this.stubGenerator.generatePropertyStub(new FakeActualPropertyDescriptor(descriptor));
            ensureClassParent(irPropertyGeneratePropertyStub, propertyDescriptor);
            PropertyGetterDescriptor getter = descriptor.getGetter();
            IrSimpleFunctionSymbol actualFunction = getter != null ? getActualFunction(getter) : null;
            PropertySetterDescriptor setter = descriptor.getSetter();
            ExpectSymbolTransformer.ActualPropertyResult actualPropertyResult2 = new ExpectSymbolTransformer.ActualPropertyResult(irPropertyGeneratePropertyStub.getSymbol(), actualFunction, setter != null ? getActualFunction(setter) : null);
            map.put(descriptor, actualPropertyResult2);
            actualPropertyResult = actualPropertyResult2;
        }
        return actualPropertyResult;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.ExpectSymbolTransformer
    protected IrConstructorSymbol getActualConstructor(ClassConstructorDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (!isOrphanedExpect(descriptor)) {
            return null;
        }
        Map<ClassConstructorDescriptor, IrConstructorSymbol> map = this.stubbedConstructors;
        IrConstructorSymbol symbol = map.get(descriptor);
        if (symbol == null) {
            symbol = this.stubGenerator.generateConstructorStub(new FakeActualClassConstructorDescriptor(descriptor)).getSymbol();
            map.put(descriptor, symbol);
        }
        return symbol;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.ExpectSymbolTransformer
    protected IrSimpleFunctionSymbol getActualFunction(FunctionDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        FunctionDescriptor functionDescriptor = descriptor;
        if (!isOrphanedExpect(functionDescriptor)) {
            return null;
        }
        Map<FunctionDescriptor, IrSimpleFunctionSymbol> map = this.stubbedFunctions;
        IrSimpleFunctionSymbol symbol = map.get(descriptor);
        if (symbol == null) {
            IrSimpleFunction irSimpleFunctionGenerateFunctionStub = this.stubGenerator.generateFunctionStub(new FakeActualFunctionDescriptor(descriptor), false);
            ensureClassParent(irSimpleFunctionGenerateFunctionStub, functionDescriptor);
            symbol = irSimpleFunctionGenerateFunctionStub.getSymbol();
            map.put(descriptor, symbol);
        }
        return symbol;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.ExpectSymbolTransformer
    protected boolean isTargetDeclaration(IrDeclaration declaration) {
        IrPropertySymbol correspondingPropertySymbol;
        IrProperty owner;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (super.isTargetDeclaration(declaration)) {
            return true;
        }
        return (declaration instanceof IrSimpleFunction) && (correspondingPropertySymbol = ((IrSimpleFunction) declaration).getCorrespondingPropertySymbol()) != null && (owner = correspondingPropertySymbol.getOwner()) != null && owner.getIsExpect();
    }

    private final boolean isOrphanedExpect(MemberDescriptor memberDescriptor) {
        return ExpectedActualResolverKt.findCompatibleActualsForExpected$default(memberDescriptor, DescriptorUtilsKt.getModule(memberDescriptor), null, 2, null).isEmpty();
    }

    private final void ensureClassParent(IrDeclaration irDeclaration, MemberDescriptor memberDescriptor) {
        IrClass irClassGenerateOrGetFacadeClass;
        if ((irDeclaration.getParent() instanceof IrClass) || (irClassGenerateOrGetFacadeClass = this.stubGenerator.generateOrGetFacadeClass(memberDescriptor)) == null) {
            return;
        }
        irDeclaration.setParent(irClassGenerateOrGetFacadeClass);
    }
}
