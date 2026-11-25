package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference2Impl;
import kotlin.jvm.internal.PropertyReference2Impl;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.backend.common.DeclarationTransformer;
import org.jetbrains.kotlin.backend.common.Mapping;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.utils.MutableReference;
import org.jetbrains.kotlin.ir.backend.js.utils.ReferenceKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: ES6PrimaryConstructorOptimizationLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0014\u001a\n\u0012\u0004\u0012\u00020\u0016\u0018\u00010\u00152\u0006\u0010\u0017\u001a\u00020\u0016H\u0016J\f\u0010\u0018\u001a\u00020\u0006*\u00020\u0007H\u0002J\f\u0010\u0019\u001a\u00020\u001a*\u00020\u0007H\u0002J\f\u0010\u001b\u001a\u00020\u0006*\u00020\u0007H\u0002J\f\u0010\u001c\u001a\u00020\u0006*\u00020\u0007H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R!\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u00020\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR?\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\r*\u00020\u00072\u000e\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\r8B@BX\u0082\u008e\u0002¢\u0006\u0012\n\u0004\b\u0013\u0010\u000b\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/ES6CollectPrimaryConstructorsWhichCouldBeOptimizedLowering;", "Lorg/jetbrains/kotlin/backend/common/DeclarationTransformer;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "needsOfBoxParameter", "", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getNeedsOfBoxParameter", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Ljava/lang/Boolean;", "needsOfBoxParameter$delegate", "Lorg/jetbrains/kotlin/backend/common/Mapping$Delegate;", "<set-?>", "Lorg/jetbrains/kotlin/ir/backend/js/utils/MutableReference;", "possibilityToOptimizeForEsClass", "getPossibilityToOptimizeForEsClass", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/ir/backend/js/utils/MutableReference;", "setPossibilityToOptimizeForEsClass", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lorg/jetbrains/kotlin/ir/backend/js/utils/MutableReference;)V", "possibilityToOptimizeForEsClass$delegate", "transformFlat", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "canBeOptimized", "checkIfCanBeOptimized", "", "hasPrimaryDelegatedToSecondaryOrSecondaryToPrimary", "isSubclassOfExternalClassWithRequiredBoxParameter", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ES6CollectPrimaryConstructorsWhichCouldBeOptimizedLowering implements DeclarationTransformer {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property2(new PropertyReference2Impl(Reflection.getOrCreateKotlinClass(ES6CollectPrimaryConstructorsWhichCouldBeOptimizedLowering.class), "needsOfBoxParameter", "getNeedsOfBoxParameter(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Ljava/lang/Boolean;")), Reflection.mutableProperty2(new MutablePropertyReference2Impl(Reflection.getOrCreateKotlinClass(ES6CollectPrimaryConstructorsWhichCouldBeOptimizedLowering.class), "possibilityToOptimizeForEsClass", "getPossibilityToOptimizeForEsClass(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/ir/backend/js/utils/MutableReference;"))};
    private final JsIrBackendContext context;

    /* JADX INFO: renamed from: needsOfBoxParameter$delegate, reason: from kotlin metadata */
    private final Mapping.Delegate needsOfBoxParameter;

    /* JADX INFO: renamed from: possibilityToOptimizeForEsClass$delegate, reason: from kotlin metadata */
    private final Mapping.Delegate possibilityToOptimizeForEsClass;

    public ES6CollectPrimaryConstructorsWhichCouldBeOptimizedLowering(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.needsOfBoxParameter = context.getMapping().getEsClassWhichNeedBoxParameters();
        this.possibilityToOptimizeForEsClass = context.getMapping().getEsClassToPossibilityForOptimization();
    }

    private final Boolean getNeedsOfBoxParameter(IrClass irClass) {
        return (Boolean) this.needsOfBoxParameter.getValue(irClass, $$delegatedProperties[0]);
    }

    private final MutableReference<Boolean> getPossibilityToOptimizeForEsClass(IrClass irClass) {
        return (MutableReference) this.possibilityToOptimizeForEsClass.getValue(irClass, $$delegatedProperties[1]);
    }

    private final void setPossibilityToOptimizeForEsClass(IrClass irClass, MutableReference<Boolean> mutableReference) {
        this.possibilityToOptimizeForEsClass.setValue(irClass, $$delegatedProperties[1], mutableReference);
    }

    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public List<IrDeclaration> transformFlat(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (!this.context.getEs6mode() || !(declaration instanceof IrClass)) {
            return null;
        }
        IrClass irClass = (IrClass) declaration;
        if (irClass.getIsExternal() || this.context.getInlineClassesUtils().isClassInlineLike(irClass) || getPossibilityToOptimizeForEsClass(irClass) != null) {
            return null;
        }
        checkIfCanBeOptimized(irClass);
        return null;
    }

    private final void checkIfCanBeOptimized(IrClass irClass) {
        MutableReference<Boolean> mutableReferenceMutableReferenceOf;
        IrClass superClass = irClass;
        while (true) {
            if (superClass == null || superClass.getIsExternal()) {
                mutableReferenceMutableReferenceOf = null;
                break;
            }
            mutableReferenceMutableReferenceOf = getPossibilityToOptimizeForEsClass(superClass);
            if (mutableReferenceMutableReferenceOf != null) {
                break;
            } else {
                superClass = IrUtilsKt.getSuperClass(superClass);
            }
        }
        if (mutableReferenceMutableReferenceOf == null) {
            mutableReferenceMutableReferenceOf = ReferenceKt.mutableReferenceOf(true);
        }
        while (irClass != null && !irClass.getIsExternal() && getPossibilityToOptimizeForEsClass(irClass) == null) {
            setPossibilityToOptimizeForEsClass(irClass, mutableReferenceMutableReferenceOf);
            if (mutableReferenceMutableReferenceOf.getValue().booleanValue() && !canBeOptimized(irClass)) {
                mutableReferenceMutableReferenceOf.setValue(false);
            }
            irClass = IrUtilsKt.getSuperClass(irClass);
        }
    }

    private final boolean canBeOptimized(IrClass irClass) {
        IrClass superClass = IrUtilsKt.getSuperClass(irClass);
        return (Intrinsics.areEqual(superClass != null ? superClass.getSymbol() : null, this.context.getThrowableClass()) || isSubclassOfExternalClassWithRequiredBoxParameter(irClass) || hasPrimaryDelegatedToSecondaryOrSecondaryToPrimary(irClass)) ? false : true;
    }

    private final boolean hasPrimaryDelegatedToSecondaryOrSecondaryToPrimary(IrClass irClass) {
        Iterable<IrFunction> iterableEmptyList;
        List<IrDeclaration> declarations = irClass.getDeclarations();
        if (declarations.isEmpty()) {
            iterableEmptyList = CollectionsKt.emptyList();
        } else {
            SmartList smartList = new SmartList();
            for (Object obj : declarations) {
                if ((obj instanceof IrFunction) && ES6ConstructorLoweringKt.isEs6ConstructorReplacement((IrFunction) obj)) {
                    smartList.add(obj);
                }
            }
            iterableEmptyList = smartList;
        }
        for (final IrFunction irFunction : iterableEmptyList) {
            final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
            IrBody body = irFunction.getBody();
            if (body != null) {
                IrElementTransformerVoidKt.transformChildrenVoid(body, new IrElementTransformerVoid() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.ES6CollectPrimaryConstructorsWhichCouldBeOptimizedLowering$hasPrimaryDelegatedToSecondaryOrSecondaryToPrimary$2$1
                    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
                    public IrExpression visitCall(IrCall expression) {
                        Intrinsics.checkNotNullParameter(expression, "expression");
                        if (ES6ConstructorLoweringKt.isSyntheticDelegatingReplacement(expression) && !Intrinsics.areEqual(expression.getSymbol().getOwner().getOrigin(), irFunction.getOrigin())) {
                            booleanRef.element = true;
                        }
                        return super.visitCall(expression);
                    }
                });
            }
            if (booleanRef.element) {
                return true;
            }
        }
        return false;
    }

    private final boolean isSubclassOfExternalClassWithRequiredBoxParameter(IrClass irClass) {
        IrClass superClass = IrUtilsKt.getSuperClass(irClass);
        return superClass != null && superClass.getIsExternal() && Intrinsics.areEqual((Object) getNeedsOfBoxParameter(irClass), (Object) true);
    }
}
