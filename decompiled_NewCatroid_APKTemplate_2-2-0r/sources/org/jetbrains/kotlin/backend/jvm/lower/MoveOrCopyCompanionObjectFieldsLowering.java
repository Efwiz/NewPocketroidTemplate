package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.UnaryOperator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.ClassLoweringPass;
import org.jetbrains.kotlin.backend.jvm.CachedFieldsForObjectInstances;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilder;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilderKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFieldBuilder;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.impl.IrAnonymousInitializerImpl;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.impl.IrExpressionBodyImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrAnonymousInitializerSymbolImpl;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationResolver;

/* JADX INFO: compiled from: MoveCompanionObjectFieldsLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0010H\u0016J\u0018\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\f\u0010\u0017\u001a\u00020\u0012*\u00020\u0010H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0018\u0010\u0007\u001a\u00020\b*\u00020\t8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/MoveOrCopyCompanionObjectFieldsLowering;", "Lorg/jetbrains/kotlin/backend/common/ClassLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "hasPublicVisibility", "", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "getHasPublicVisibility", "(Lorg/jetbrains/kotlin/ir/declarations/IrProperty;)Z", "copyConstProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "oldProperty", "newParent", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "lower", "", "irClass", "makeAnonymousInitializerStatic", "Lorg/jetbrains/kotlin/ir/declarations/IrAnonymousInitializer;", "oldInitializer", "handle", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class MoveOrCopyCompanionObjectFieldsLowering implements ClassLoweringPass {
    private final JvmBackendContext context;

    public MoveOrCopyCompanionObjectFieldsLowering(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final JvmBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.ClassLoweringPass
    public void lower(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        if (IrUtilsKt.isNonCompanionObject(irClass)) {
            handle(irClass);
            return;
        }
        Iterator<T> it = irClass.getDeclarations().iterator();
        Object obj = null;
        boolean z = false;
        Object obj2 = null;
        while (true) {
            if (!it.hasNext()) {
                if (!z) {
                    break;
                }
                obj = obj2;
                break;
            }
            Object next = it.next();
            IrDeclaration irDeclaration = (IrDeclaration) next;
            if ((irDeclaration instanceof IrClass) && ((IrClass) irDeclaration).getIsCompanion()) {
                if (z) {
                    break;
                }
                z = true;
                obj2 = next;
            }
        }
        IrClass irClass2 = (IrClass) obj;
        if (irClass2 != null) {
            handle(irClass2);
        }
    }

    private final void handle(IrClass irClass) {
        Object obj;
        final IrClass parentAsClass;
        IrProperty irProperty;
        IrField staticBackingField;
        List<IrDeclaration> declarations = irClass.getDeclarations();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(declarations, 10));
        Iterator<T> it = declarations.iterator();
        while (true) {
            obj = null;
            if (!it.hasNext()) {
                break;
            }
            IrDeclaration irDeclaration = (IrDeclaration) it.next();
            if ((irDeclaration instanceof IrProperty) && (staticBackingField = this.context.getCachedDeclarations().getStaticBackingField((irProperty = (IrProperty) irDeclaration))) != null) {
                irProperty.setBackingField(staticBackingField);
                staticBackingField.setCorrespondingPropertySymbol(irProperty.getSymbol());
                obj = staticBackingField;
            }
            arrayList.add(obj);
        }
        ArrayList<IrField> arrayList2 = arrayList;
        IrClass parentAsClass2 = irClass.getIsCompanion() ? IrUtilsKt.getParentAsClass(irClass) : null;
        for (Object obj2 : arrayList2) {
            if (((IrField) obj2) != null) {
                obj = obj2;
                break;
            }
        }
        IrField irField = (IrField) obj;
        if (irField == null || (parentAsClass = IrUtilsKt.getParentAsClass(irField)) == null) {
            parentAsClass = parentAsClass2 == null ? irClass : parentAsClass2;
        }
        if (parentAsClass == irClass) {
            irClass.getDeclarations().replaceAll(new UnaryOperator() { // from class: org.jetbrains.kotlin.backend.jvm.lower.MoveOrCopyCompanionObjectFieldsLowering$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj3) {
                    return MoveOrCopyCompanionObjectFieldsLowering.handle$lambda$4(this.f$0, parentAsClass, (IrDeclaration) obj3);
                }
            });
            if (parentAsClass2 != null) {
                for (IrDeclaration irDeclaration2 : irClass.getDeclarations()) {
                    if (irDeclaration2 instanceof IrProperty) {
                        IrProperty irProperty2 = (IrProperty) irDeclaration2;
                        if (irProperty2.getIsConst() && getHasPublicVisibility(irProperty2)) {
                            copyConstProperty(irProperty2, parentAsClass2);
                        }
                    }
                }
                return;
            }
            return;
        }
        int i = 0;
        for (IrField irField2 : arrayList2) {
            int i2 = i + 1;
            if (irField2 != null) {
                parentAsClass.getDeclarations().add(irField2);
            }
            if (irClass.getDeclarations().get(i) instanceof IrAnonymousInitializer) {
                List<IrDeclaration> declarations2 = parentAsClass.getDeclarations();
                IrDeclaration irDeclaration3 = irClass.getDeclarations().get(i);
                Intrinsics.checkNotNull(irDeclaration3, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer");
                declarations2.add(makeAnonymousInitializerStatic((IrAnonymousInitializer) irDeclaration3, parentAsClass));
            }
            i = i2;
        }
        CollectionsKt.removeAll((List) irClass.getDeclarations(), (Function1) new Function1<IrDeclaration, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.MoveOrCopyCompanionObjectFieldsLowering.handle.2
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrDeclaration it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return Boolean.valueOf(it2 instanceof IrAnonymousInitializer);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrDeclaration handle$lambda$4(MoveOrCopyCompanionObjectFieldsLowering this$0, IrClass newParent, IrDeclaration it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(newParent, "$newParent");
        Intrinsics.checkNotNullParameter(it, "it");
        return it instanceof IrAnonymousInitializer ? this$0.makeAnonymousInitializerStatic((IrAnonymousInitializer) it, newParent) : it;
    }

    private final boolean getHasPublicVisibility(IrProperty irProperty) {
        return (DescriptorVisibilities.isPrivate(irProperty.getVisibility()) || Intrinsics.areEqual(irProperty.getVisibility(), DescriptorVisibilities.PROTECTED)) ? false : true;
    }

    private final IrAnonymousInitializer makeAnonymousInitializerStatic(IrAnonymousInitializer oldInitializer, IrClass newParent) {
        IrClass parentAsClass = IrUtilsKt.getParentAsClass(oldInitializer);
        IrAnonymousInitializerImpl irAnonymousInitializerImpl = new IrAnonymousInitializerImpl(oldInitializer.getStartOffset(), oldInitializer.getEndOffset(), oldInitializer.getOrigin(), new IrAnonymousInitializerSymbolImpl(newParent.getSymbol()), true, null, 32, null);
        IrClass irClass = newParent;
        irAnonymousInitializerImpl.setParent(irClass);
        irAnonymousInitializerImpl.setBody((IrBlockBody) PatchDeclarationParentsKt.patchDeclarationParents(oldInitializer.getBody(), irClass));
        CachedFieldsForObjectInstances fieldsForObjectInstances = this.context.getCachedDeclarations().getFieldsForObjectInstances();
        IrValueParameter thisReceiver = parentAsClass.getThisReceiver();
        Intrinsics.checkNotNull(thisReceiver);
        JvmIrUtilsKt.replaceThisByStaticReference(irAnonymousInitializerImpl, fieldsForObjectInstances, parentAsClass, thisReceiver);
        return irAnonymousInitializerImpl;
    }

    private final IrField copyConstProperty(IrProperty oldProperty, IrClass newParent) {
        IrExpressionBodyImpl irExpressionBodyImpl;
        IrField backingField = oldProperty.getBackingField();
        Intrinsics.checkNotNull(backingField);
        IrFactory factory = newParent.getFactory();
        IrFieldBuilder irFieldBuilder = new IrFieldBuilder();
        irFieldBuilder.updateFrom(backingField);
        irFieldBuilder.setName(backingField.getName());
        irFieldBuilder.setStatic(true);
        IrField irFieldBuildField = DeclarationBuildersKt.buildField(factory, irFieldBuilder);
        IrClass irClass = newParent;
        irFieldBuildField.setParent(irClass);
        newParent.getDeclarations().add(irFieldBuildField);
        irFieldBuildField.setParent(irClass);
        irFieldBuildField.setCorrespondingPropertySymbol(oldProperty.getSymbol());
        IrExpressionBody initializer = backingField.getInitializer();
        if (initializer != null) {
            int startOffset = initializer.getStartOffset();
            int endOffset = initializer.getEndOffset();
            IrExpression expression = initializer.getExpression();
            Intrinsics.checkNotNull(expression, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrConst<*>");
            irExpressionBodyImpl = new IrExpressionBodyImpl(startOffset, endOffset, IrUtilsKt.shallowCopy(expression));
        } else {
            irExpressionBodyImpl = null;
        }
        irFieldBuildField.setInitializer(irExpressionBodyImpl);
        irFieldBuildField.setAnnotations(CollectionsKt.plus((Collection) irFieldBuildField.getAnnotations(), (Iterable) backingField.getAnnotations()));
        if (Intrinsics.areEqual(IrUtilsKt.getParentAsClass(oldProperty).getVisibility(), DescriptorVisibilities.PRIVATE)) {
            JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder$default = JvmIrBuilderKt.createJvmIrBuilder$default(this.context, irFieldBuildField.getSymbol(), 0, 0, 6, null);
            irFieldBuildField.setAnnotations(CollectionsKt.plus((Collection<? extends IrConstructorCall>) AdditionalIrUtilsKt.filterOutAnnotations(DeprecationResolver.INSTANCE.getJAVA_DEPRECATED(), irFieldBuildField.getAnnotations()), ExpressionHelpersKt.irCall((IrBuilderWithScope) jvmIrBuilderCreateJvmIrBuilder$default, jvmIrBuilderCreateJvmIrBuilder$default.getIrSymbols().getJavaLangDeprecatedConstructorWithDeprecatedFlag())));
        }
        return irFieldBuildField;
    }
}
