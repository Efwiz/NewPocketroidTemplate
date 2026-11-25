package org.jetbrains.kotlin.fir.backend.jvm;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.fir.backend.Fir2IrSpecialSymbolProvider;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrClassBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.impl.IrExternalPackageFragmentImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.DescriptorlessExternalPackageFragmentSymbol;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: Fir2IrJvmSpecialAnnotationSymbolProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00142\u00020\u0001:\u0001\u0014B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0014\u0010\u0010\u001a\u00020\u0011*\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002R\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u001b\u0010\t\u001a\u00020\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\b\u001a\u0004\b\n\u0010\u0006¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/jvm/Fir2IrJvmSpecialAnnotationSymbolProvider;", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrSpecialSymbolProvider;", "()V", "kotlinInternalIrPackage", "Lorg/jetbrains/kotlin/ir/declarations/impl/IrExternalPackageFragmentImpl;", "getKotlinInternalIrPackage", "()Lorg/jetbrains/kotlin/ir/declarations/impl/IrExternalPackageFragmentImpl;", "kotlinInternalIrPackage$delegate", "Lkotlin/Lazy;", "kotlinJvmInternalPackage", "getKotlinJvmInternalPackage", "kotlinJvmInternalPackage$delegate", "getClassSymbolById", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "id", "Lorg/jetbrains/kotlin/name/ClassId;", "toIrClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "parent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "Companion", "jvm-backend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Fir2IrJvmSpecialAnnotationSymbolProvider extends Fir2IrSpecialSymbolProvider {
    private static final ClassId ENHANCED_NULLABILITY_ID;
    private static final ClassId FLEXIBLE_MUTABILITY_ID;
    private static final ClassId FLEXIBLE_NULLABILITY_ID;
    private static final ClassId RAW_TYPE_ANNOTATION_ID;

    /* JADX INFO: renamed from: kotlinJvmInternalPackage$delegate, reason: from kotlin metadata */
    private final Lazy kotlinJvmInternalPackage = LazyKt.lazy(new Function0<IrExternalPackageFragmentImpl>() { // from class: org.jetbrains.kotlin.fir.backend.jvm.Fir2IrJvmSpecialAnnotationSymbolProvider$kotlinJvmInternalPackage$2
        @Override // kotlin.jvm.functions.Function0
        public final IrExternalPackageFragmentImpl invoke() {
            DescriptorlessExternalPackageFragmentSymbol descriptorlessExternalPackageFragmentSymbol = new DescriptorlessExternalPackageFragmentSymbol();
            FqName packageFqName = Fir2IrJvmSpecialAnnotationSymbolProvider.ENHANCED_NULLABILITY_ID.getPackageFqName();
            Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
            return new IrExternalPackageFragmentImpl(descriptorlessExternalPackageFragmentSymbol, packageFqName);
        }
    });

    /* JADX INFO: renamed from: kotlinInternalIrPackage$delegate, reason: from kotlin metadata */
    private final Lazy kotlinInternalIrPackage = LazyKt.lazy(new Function0<IrExternalPackageFragmentImpl>() { // from class: org.jetbrains.kotlin.fir.backend.jvm.Fir2IrJvmSpecialAnnotationSymbolProvider$kotlinInternalIrPackage$2
        @Override // kotlin.jvm.functions.Function0
        public final IrExternalPackageFragmentImpl invoke() {
            DescriptorlessExternalPackageFragmentSymbol descriptorlessExternalPackageFragmentSymbol = new DescriptorlessExternalPackageFragmentSymbol();
            FqName packageFqName = Fir2IrJvmSpecialAnnotationSymbolProvider.FLEXIBLE_NULLABILITY_ID.getPackageFqName();
            Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
            return new IrExternalPackageFragmentImpl(descriptorlessExternalPackageFragmentSymbol, packageFqName);
        }
    });

    private final IrExternalPackageFragmentImpl getKotlinJvmInternalPackage() {
        return (IrExternalPackageFragmentImpl) this.kotlinJvmInternalPackage.getValue();
    }

    private final IrExternalPackageFragmentImpl getKotlinInternalIrPackage() {
        return (IrExternalPackageFragmentImpl) this.kotlinInternalIrPackage.getValue();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrSpecialSymbolProvider
    public IrClassSymbol getClassSymbolById(ClassId id2) {
        Intrinsics.checkNotNullParameter(id2, "id");
        if (Intrinsics.areEqual(id2, ENHANCED_NULLABILITY_ID)) {
            return toIrClass(id2, getKotlinJvmInternalPackage()).getSymbol();
        }
        if (Intrinsics.areEqual(id2, FLEXIBLE_NULLABILITY_ID) || Intrinsics.areEqual(id2, FLEXIBLE_MUTABILITY_ID) || Intrinsics.areEqual(id2, RAW_TYPE_ANNOTATION_ID)) {
            return toIrClass(id2, getKotlinInternalIrPackage()).getSymbol();
        }
        return null;
    }

    private final IrClass toIrClass(ClassId classId, IrDeclarationParent irDeclarationParent) {
        IrFactory irFactory = getComponents().getIrFactory();
        IrClassBuilder irClassBuilder = new IrClassBuilder();
        irClassBuilder.setKind(ClassKind.ANNOTATION_CLASS);
        Name shortClassName = classId.getShortClassName();
        Intrinsics.checkNotNullExpressionValue(shortClassName, "getShortClassName(...)");
        irClassBuilder.setName(shortClassName);
        IrClass irClassBuildClass = DeclarationBuildersKt.buildClass(irFactory, irClassBuilder);
        IrUtilsKt.createImplicitParameterDeclarationWithWrappedDescriptor(irClassBuildClass);
        irClassBuildClass.setParent(irDeclarationParent);
        IrFactory factory = irClassBuildClass.getFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        irFunctionBuilder.setPrimary(true);
        irFunctionBuilder.setReturnType(IrUtilsKt.getDefaultType(irClassBuildClass));
        IrConstructor irConstructorBuildConstructor = DeclarationBuildersKt.buildConstructor(factory, irFunctionBuilder);
        irClassBuildClass.getDeclarations().add(irConstructorBuildConstructor);
        irConstructorBuildConstructor.setParent(irClassBuildClass);
        return irClassBuildClass;
    }

    static {
        ClassId classId = ClassId.topLevel(JvmAnnotationNames.ENHANCED_NULLABILITY_ANNOTATION);
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        ENHANCED_NULLABILITY_ID = classId;
        ClassId classId2 = ClassId.topLevel(IrBuiltIns.INSTANCE.getKOTLIN_INTERNAL_IR_FQN().child(Name.identifier("FlexibleNullability")));
        Intrinsics.checkNotNullExpressionValue(classId2, "topLevel(...)");
        FLEXIBLE_NULLABILITY_ID = classId2;
        ClassId classId3 = ClassId.topLevel(IrBuiltIns.INSTANCE.getKOTLIN_INTERNAL_IR_FQN().child(Name.identifier("FlexibleMutability")));
        Intrinsics.checkNotNullExpressionValue(classId3, "topLevel(...)");
        FLEXIBLE_MUTABILITY_ID = classId3;
        ClassId classId4 = ClassId.topLevel(IrBuiltIns.INSTANCE.getKOTLIN_INTERNAL_IR_FQN().child(Name.identifier("RawType")));
        Intrinsics.checkNotNullExpressionValue(classId4, "topLevel(...)");
        RAW_TYPE_ANNOTATION_ID = classId4;
    }
}
