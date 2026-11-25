package org.jetbrains.kotlin.resolve.jvm.checkers;

import java.util.Collection;
import java.util.LinkedHashSet;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilityUtils;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.load.java.descriptors.JavaClassDescriptor;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.calls.checkers.AdditionalTypeChecker;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeProjection;

/* JADX INFO: compiled from: JavaTypeAccessibilityChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001f\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J,\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u000bH\u0016J\"\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010\u000f\u001a\u00020\b2\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u000bH\u0002J.\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0002¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/JavaTypeAccessibilityChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/AdditionalTypeChecker;", "()V", "checkType", "", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "expressionType", "Lorg/jetbrains/kotlin/types/KotlinType;", "expressionTypeWithSmartCast", "c", "Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionContext;", "findInaccessibleJavaClasses", "", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", ModuleXmlParser.TYPE, "findInaccessibleJavaClassesRec", "scopeOwner", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "inaccessibleClasses", "", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaTypeAccessibilityChecker implements AdditionalTypeChecker {
    @Override // org.jetbrains.kotlin.resolve.calls.checkers.AdditionalTypeChecker
    public void checkType(KtExpression expression, KotlinType expressionType, KotlinType expressionTypeWithSmartCast, ResolutionContext<?> c) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(expressionType, "expressionType");
        Intrinsics.checkNotNullParameter(expressionTypeWithSmartCast, "expressionTypeWithSmartCast");
        Intrinsics.checkNotNullParameter(c, "c");
        if (c.isDebuggerContext) {
            return;
        }
        Collection<ClassDescriptor> collectionFindInaccessibleJavaClasses = findInaccessibleJavaClasses(expressionType, c);
        if (!collectionFindInaccessibleJavaClasses.isEmpty()) {
            c.trace.report(Errors.INACCESSIBLE_TYPE.on(expression, expressionType, collectionFindInaccessibleJavaClasses));
        } else {
            if (Intrinsics.areEqual(expressionTypeWithSmartCast, expressionType)) {
                return;
            }
            Collection<ClassDescriptor> collectionFindInaccessibleJavaClasses2 = findInaccessibleJavaClasses(expressionTypeWithSmartCast, c);
            if (collectionFindInaccessibleJavaClasses2.isEmpty()) {
                return;
            }
            c.trace.report(Errors.INACCESSIBLE_TYPE.on(expression, expressionType, collectionFindInaccessibleJavaClasses2));
        }
    }

    private final Collection<ClassDescriptor> findInaccessibleJavaClasses(KotlinType type, ResolutionContext<?> c) {
        DeclarationDescriptor ownerDescriptor = c.scope.getOwnerDescriptor();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        LanguageVersionSettings languageVersionSettings = c.languageVersionSettings;
        Intrinsics.checkNotNullExpressionValue(languageVersionSettings, "languageVersionSettings");
        findInaccessibleJavaClassesRec(type, ownerDescriptor, linkedHashSet, languageVersionSettings);
        return linkedHashSet;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void findInaccessibleJavaClassesRec(KotlinType type, DeclarationDescriptor scopeOwner, Collection<ClassDescriptor> inaccessibleClasses, LanguageVersionSettings languageVersionSettings) {
        ClassifierDescriptor this$0 = type.getConstructor().getThis$0();
        if ((this$0 instanceof JavaClassDescriptor) && !DescriptorVisibilityUtils.isVisibleIgnoringReceiver((DeclarationDescriptorWithVisibility) this$0, scopeOwner, languageVersionSettings)) {
            inaccessibleClasses.add(this$0);
        }
        for (TypeProjection typeProjection : type.getArguments()) {
            if (!typeProjection.isStarProjection()) {
                KotlinType nullableAnyType = typeProjection.getNullableAnyType();
                Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getType(...)");
                findInaccessibleJavaClassesRec(nullableAnyType, scopeOwner, inaccessibleClasses, languageVersionSettings);
            }
        }
    }
}
