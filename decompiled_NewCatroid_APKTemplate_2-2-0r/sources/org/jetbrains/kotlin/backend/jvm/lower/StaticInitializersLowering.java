package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.description.method.MethodDescription;
import org.jetbrains.kotlin.backend.common.ClassLoweringPass;
import org.jetbrains.kotlin.backend.common.lower.InitializersLoweringBase;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrSetField;
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockBodyImpl;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.load.java.JavaDescriptorVisibilities;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: StaticInitializersLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \f2\u00020\u00012\u00020\u0002:\u0001\fB\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/StaticInitializersLowering;", "Lorg/jetbrains/kotlin/backend/common/lower/InitializersLoweringBase;", "Lorg/jetbrains/kotlin/backend/common/ClassLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "lower", "", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "Companion", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StaticInitializersLowering extends InitializersLoweringBase implements ClassLoweringPass {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Name clinitName;
    private final JvmBackendContext context;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StaticInitializersLowering(JvmBackendContext context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.InitializersLoweringBase
    public JvmBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.ClassLoweringPass
    public void lower(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        List mutableList = CollectionsKt.toMutableList((Collection) extractInitializers(irClass, new Function1<IrDeclaration, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.StaticInitializersLowering$lower$staticInitializerStatements$1
            /* JADX WARN: Code duplicated, block: B:12:0x0024  */
            /* JADX WARN: Code duplicated, block: B:8:0x0018  */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrDeclaration it) {
                boolean z;
                Intrinsics.checkNotNullParameter(it, "it");
                if (it instanceof IrField) {
                    IrField irField = (IrField) it;
                    if (irField.getIsStatic() && JvmIrUtilsKt.constantValue(irField) == null) {
                        z = true;
                    } else if ((it instanceof IrAnonymousInitializer) || !((IrAnonymousInitializer) it).getIsStatic()) {
                        z = false;
                    } else {
                        z = true;
                    }
                } else {
                    if (it instanceof IrAnonymousInitializer) {
                    }
                    z = false;
                }
                return Boolean.valueOf(z);
            }
        }));
        if (mutableList.isEmpty()) {
            return;
        }
        if (mutableList.size() > 1) {
            CollectionsKt.sortWith(mutableList, new Comparator() { // from class: org.jetbrains.kotlin.backend.jvm.lower.StaticInitializersLowering$lower$$inlined$sortBy$1
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    int i;
                    IrFieldSymbol symbol;
                    IrField owner;
                    IrFieldSymbol symbol2;
                    IrField owner2;
                    IrStatement irStatement = (IrStatement) t;
                    IrDeclarationOrigin origin = null;
                    IrSetField irSetField = irStatement instanceof IrSetField ? (IrSetField) irStatement : null;
                    IrDeclarationOrigin origin2 = (irSetField == null || (symbol2 = irSetField.getSymbol()) == null || (owner2 = symbol2.getOwner()) == null) ? null : owner2.getOrigin();
                    int i2 = 5;
                    if (Intrinsics.areEqual(origin2, JvmLoweredDeclarationOrigin.GENERATED_PROPERTY_REFERENCE.INSTANCE)) {
                        i = 1;
                    } else if (Intrinsics.areEqual(origin2, IrDeclarationOrigin.FIELD_FOR_ENUM_ENTRY.INSTANCE)) {
                        i = 2;
                    } else if (Intrinsics.areEqual(origin2, IrDeclarationOrigin.FIELD_FOR_ENUM_VALUES.INSTANCE)) {
                        i = 3;
                    } else if (Intrinsics.areEqual(origin2, IrDeclarationOrigin.FIELD_FOR_ENUM_ENTRIES.INSTANCE)) {
                        i = 4;
                    } else {
                        i = Intrinsics.areEqual(origin2, IrDeclarationOrigin.FIELD_FOR_OBJECT_INSTANCE.INSTANCE) ? 5 : 6;
                    }
                    Integer numValueOf = Integer.valueOf(i);
                    IrStatement irStatement2 = (IrStatement) t2;
                    IrSetField irSetField2 = irStatement2 instanceof IrSetField ? (IrSetField) irStatement2 : null;
                    if (irSetField2 != null && (symbol = irSetField2.getSymbol()) != null && (owner = symbol.getOwner()) != null) {
                        origin = owner.getOrigin();
                    }
                    if (Intrinsics.areEqual(origin, JvmLoweredDeclarationOrigin.GENERATED_PROPERTY_REFERENCE.INSTANCE)) {
                        i2 = 1;
                    } else if (Intrinsics.areEqual(origin, IrDeclarationOrigin.FIELD_FOR_ENUM_ENTRY.INSTANCE)) {
                        i2 = 2;
                    } else if (Intrinsics.areEqual(origin, IrDeclarationOrigin.FIELD_FOR_ENUM_VALUES.INSTANCE)) {
                        i2 = 3;
                    } else if (Intrinsics.areEqual(origin, IrDeclarationOrigin.FIELD_FOR_ENUM_ENTRIES.INSTANCE)) {
                        i2 = 4;
                    } else if (!Intrinsics.areEqual(origin, IrDeclarationOrigin.FIELD_FOR_OBJECT_INSTANCE.INSTANCE)) {
                        i2 = 6;
                    }
                    return ComparisonsKt.compareValues(numValueOf, Integer.valueOf(i2));
                }
            });
        }
        IrFactory factory = irClass.getFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        irFunctionBuilder.setStartOffset(irClass.getStartOffset());
        irFunctionBuilder.setEndOffset(irClass.getEndOffset());
        irFunctionBuilder.setName(clinitName);
        irFunctionBuilder.setOrigin(JvmLoweredDeclarationOrigin.CLASS_STATIC_INITIALIZER.INSTANCE);
        irFunctionBuilder.setReturnType(getContext().getIrBuiltIns().getUnitType());
        DescriptorVisibility PACKAGE_VISIBILITY = JavaDescriptorVisibilities.PACKAGE_VISIBILITY;
        Intrinsics.checkNotNullExpressionValue(PACKAGE_VISIBILITY, "PACKAGE_VISIBILITY");
        irFunctionBuilder.setVisibility(PACKAGE_VISIBILITY);
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(factory, irFunctionBuilder);
        IrClass irClass2 = irClass;
        irClass2.getDeclarations().add(irSimpleFunctionBuildFunction);
        irSimpleFunctionBuildFunction.setParent(irClass2);
        irSimpleFunctionBuildFunction.setBody((IrBody) PatchDeclarationParentsKt.patchDeclarationParents(new IrBlockBodyImpl(irClass.getStartOffset(), irClass.getEndOffset(), mutableList), irSimpleFunctionBuildFunction));
    }

    /* JADX INFO: compiled from: StaticInitializersLowering.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/StaticInitializersLowering$Companion;", "", "()V", "clinitName", "Lorg/jetbrains/kotlin/name/Name;", "getClinitName", "()Lorg/jetbrains/kotlin/name/Name;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Name getClinitName() {
            return StaticInitializersLowering.clinitName;
        }
    }

    static {
        Name nameSpecial = Name.special(MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME);
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        clinitName = nameSpecial;
    }
}
