package org.jetbrains.kotlin.resolve.scopes;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorNonRoot;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.OverloadChecker;

/* JADX INFO: compiled from: LocalRedeclarationChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0018\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH$J\u0018\u0010\u0011\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\fH$R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/AbstractLocalRedeclarationChecker;", "Lorg/jetbrains/kotlin/resolve/scopes/LocalRedeclarationChecker;", "overloadChecker", "Lorg/jetbrains/kotlin/resolve/OverloadChecker;", "(Lorg/jetbrains/kotlin/resolve/OverloadChecker;)V", "getOverloadChecker", "()Lorg/jetbrains/kotlin/resolve/OverloadChecker;", "checkBeforeAddingToScope", "", "scope", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "newDescriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "handleConflictingOverloads", "first", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "second", "handleRedeclaration", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractLocalRedeclarationChecker implements LocalRedeclarationChecker {
    private final OverloadChecker overloadChecker;

    protected abstract void handleConflictingOverloads(CallableMemberDescriptor first, CallableMemberDescriptor second);

    protected abstract void handleRedeclaration(DeclarationDescriptor first, DeclarationDescriptor second);

    public AbstractLocalRedeclarationChecker(OverloadChecker overloadChecker) {
        Intrinsics.checkNotNullParameter(overloadChecker, "overloadChecker");
        this.overloadChecker = overloadChecker;
    }

    public final OverloadChecker getOverloadChecker() {
        return this.overloadChecker;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.LocalRedeclarationChecker
    public void checkBeforeAddingToScope(LexicalScope scope, DeclarationDescriptor newDescriptor) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(newDescriptor, "newDescriptor");
        Name name2 = newDescriptor.getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        NoLookupLocation noLookupLocation = NoLookupLocation.WHEN_CHECK_DECLARATION_CONFLICTS;
        if (newDescriptor instanceof ClassifierDescriptor ? true : newDescriptor instanceof VariableDescriptor) {
            NoLookupLocation noLookupLocation2 = noLookupLocation;
            ClassifierDescriptor contributedClassifier = scope.mo7465getContributedClassifier(name2, noLookupLocation2);
            ClassifierDescriptor classifierDescriptor = contributedClassifier != null ? contributedClassifier : (DeclarationDescriptorNonRoot) CollectionsKt.firstOrNull(scope.getContributedVariables(name2, noLookupLocation2));
            if (classifierDescriptor != null) {
                handleRedeclaration(classifierDescriptor, newDescriptor);
                return;
            }
            return;
        }
        if (newDescriptor instanceof FunctionDescriptor) {
            NoLookupLocation noLookupLocation3 = noLookupLocation;
            List contributedFunctions = scope.getContributedFunctions(name2, noLookupLocation3);
            ClassifierDescriptor contributedClassifier2 = scope.mo7465getContributedClassifier(name2, noLookupLocation3);
            if (contributedClassifier2 instanceof ClassDescriptor) {
                Collection<ClassConstructorDescriptor> constructors = ((ClassDescriptor) contributedClassifier2).getConstructors();
                Intrinsics.checkNotNullExpressionValue(constructors, "getConstructors(...)");
                contributedFunctions = CollectionsKt.plus((Collection) contributedFunctions, (Iterable) constructors);
            }
            for (FunctionDescriptor functionDescriptor : contributedFunctions) {
                OverloadChecker overloadChecker = this.overloadChecker;
                Intrinsics.checkNotNull(functionDescriptor);
                if (!overloadChecker.isOverloadable(functionDescriptor, newDescriptor)) {
                    handleConflictingOverloads((CallableMemberDescriptor) newDescriptor, functionDescriptor);
                    return;
                }
            }
            return;
        }
        throw new IllegalStateException("Unexpected type of descriptor: " + newDescriptor.getClass().getName() + ", descriptor: " + newDescriptor);
    }
}
