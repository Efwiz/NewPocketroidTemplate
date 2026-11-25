package org.jetbrains.kotlin.resolve.checkers;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: KClassWithIncorrectTypeArgumentChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\f\u0010\u000b\u001a\u00020\f*\u00020\rH\u0002¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/KClassWithIncorrectTypeArgumentChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "isKClassWithBadArgument", "", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KClassWithIncorrectTypeArgumentChecker implements DeclarationChecker {
    public static final KClassWithIncorrectTypeArgumentChecker INSTANCE = new KClassWithIncorrectTypeArgumentChecker();

    private KClassWithIncorrectTypeArgumentChecker() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, final DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        KotlinType returnType;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (descriptor instanceof CallableMemberDescriptor) {
            CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) descriptor;
            if (Intrinsics.areEqual(callableMemberDescriptor.getVisibilityImpl(), DescriptorVisibilities.LOCAL) || !(declaration instanceof KtCallableDeclaration) || ((KtCallableDeclaration) declaration).mo7396getTypeReference() != null || (descriptor instanceof PropertyAccessorDescriptor) || (returnType = callableMemberDescriptor.getReturnType()) == null) {
                return;
            }
            final Ref.ObjectRef objectRef = new Ref.ObjectRef();
            TypeUtilsKt.contains(returnType, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.checkers.KClassWithIncorrectTypeArgumentChecker.check.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                /* JADX WARN: Type inference failed for: r5v7, types: [T, org.jetbrains.kotlin.descriptors.ClassifierDescriptor] */
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(UnwrappedType type) {
                    TypeProjection typeProjection;
                    KotlinType nullableAnyType;
                    TypeConstructor constructor;
                    ?? this$0;
                    Intrinsics.checkNotNullParameter(type, "type");
                    boolean zIsKClassWithBadArgument = KClassWithIncorrectTypeArgumentChecker.INSTANCE.isKClassWithBadArgument(type);
                    if (zIsKClassWithBadArgument && (typeProjection = (TypeProjection) CollectionsKt.singleOrNull((List) type.getArguments())) != null && (nullableAnyType = typeProjection.getNullableAnyType()) != null && (constructor = nullableAnyType.getConstructor()) != null && (this$0 = constructor.getThis$0()) != 0) {
                        DeclarationDescriptor declarationDescriptor = descriptor;
                        Ref.ObjectRef<TypeParameterDescriptor> objectRef2 = objectRef;
                        if ((this$0 instanceof TypeParameterDescriptor) && Intrinsics.areEqual(((TypeParameterDescriptor) this$0).getContainingModule(), declarationDescriptor)) {
                            objectRef2.element = this$0;
                        }
                    }
                    return Boolean.valueOf(zIsKClassWithBadArgument);
                }
            });
            if (objectRef.element != 0) {
                BindingTrace trace = context.getTrace();
                T t = objectRef.element;
                Intrinsics.checkNotNull(t);
                trace.report(Errors.KCLASS_WITH_NULLABLE_TYPE_PARAMETER_IN_SIGNATURE.on(declaration, t));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isKClassWithBadArgument(UnwrappedType unwrappedType) {
        TypeProjection typeProjection = (TypeProjection) CollectionsKt.singleOrNull((List) unwrappedType.getArguments());
        if (typeProjection == null) {
            return false;
        }
        UnwrappedType unwrappedTypeUnwrap = typeProjection.isStarProjection() ? null : typeProjection.getNullableAnyType().unwrap();
        if (unwrappedTypeUnwrap == null) {
            return false;
        }
        ClassifierDescriptor this$0 = unwrappedType.getConstructor().getThis$0();
        ClassDescriptor classDescriptor = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
        if (classDescriptor == null || !KotlinBuiltIns.isKClass(classDescriptor)) {
            return false;
        }
        UnwrappedType unwrappedType2 = unwrappedTypeUnwrap;
        SimpleType anyType = TypeUtilsKt.getBuiltIns(unwrappedType2).getAnyType();
        Intrinsics.checkNotNullExpressionValue(anyType, "getAnyType(...)");
        return !TypeUtilsKt.isSubtypeOf(unwrappedType2, anyType);
    }
}
