package org.jetbrains.kotlin.resolve.jvm.checkers;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.resolve.calls.checkers.CallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.TypeAttributes;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeProjectionImpl;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;

/* JADX INFO: compiled from: JavaClassOnCompanionChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/JavaClassOnCompanionChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaClassOnCompanionChecker implements CallChecker {
    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        KotlinType nullableAnyType;
        TypeConstructor constructor;
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        if (resultingDescriptor instanceof PropertyDescriptor) {
            PropertyDescriptor propertyDescriptor = (PropertyDescriptor) resultingDescriptor;
            if (Intrinsics.areEqual(propertyDescriptor.getShortName().asString(), "javaClass")) {
                DeclarationDescriptor containingDeclaration = propertyDescriptor.getContainingDeclaration();
                Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
                if ((containingDeclaration instanceof PackageFragmentDescriptor) && Intrinsics.areEqual(((PackageFragmentDescriptor) containingDeclaration).getFqName().asString(), "kotlin.jvm")) {
                    KotlinType type = propertyDescriptor.getType();
                    Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                    TypeProjection typeProjection = (TypeProjection) CollectionsKt.singleOrNull((List) type.getArguments());
                    ClassifierDescriptor this$0 = (typeProjection == null || (nullableAnyType = typeProjection.getNullableAnyType()) == null || (constructor = nullableAnyType.getConstructor()) == null) ? null : constructor.getThis$0();
                    ClassDescriptor classDescriptor = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
                    if (classDescriptor != null && classDescriptor.getIsCompanionObject()) {
                        DeclarationDescriptor containingDeclaration2 = classDescriptor.getContainingDeclaration();
                        Intrinsics.checkNotNull(containingDeclaration2, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                        ClassDescriptor classDescriptor2 = (ClassDescriptor) containingDeclaration2;
                        ClassifierDescriptor this$1 = type.getConstructor().getThis$0();
                        ClassDescriptor classDescriptor3 = this$1 instanceof ClassDescriptor ? (ClassDescriptor) this$1 : null;
                        if (classDescriptor3 == null) {
                            return;
                        }
                        List listListOf = CollectionsKt.listOf(new TypeProjectionImpl(classDescriptor2.getDefaultType()));
                        TypeAttributes empty = TypeAttributes.INSTANCE.getEmpty();
                        TypeConstructor typeConstructor = classDescriptor3.getTypeConstructor();
                        Intrinsics.checkNotNullExpressionValue(typeConstructor, "getTypeConstructor(...)");
                        context.getTrace().report(ErrorsJvm.JAVA_CLASS_ON_COMPANION.on(reportOn, type, KotlinTypeFactory.simpleType$default(empty, typeConstructor, listListOf, type.getIsMarkedNullable(), (KotlinTypeRefiner) null, 16, (Object) null)));
                    }
                }
            }
        }
    }
}
