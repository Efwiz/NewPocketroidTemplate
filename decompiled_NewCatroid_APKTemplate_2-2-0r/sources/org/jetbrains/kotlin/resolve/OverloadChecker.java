package org.jetbrains.kotlin.resolve;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilderImpl;
import org.jetbrains.kotlin.resolve.calls.results.FlatSignature;
import org.jetbrains.kotlin.resolve.calls.results.FlatSignatureKt;
import org.jetbrains.kotlin.resolve.calls.results.FlatSignatureUtilsKt;
import org.jetbrains.kotlin.resolve.calls.results.OverloadabilitySpecificityCallbacks;
import org.jetbrains.kotlin.resolve.calls.results.TypeSpecificityComparator;
import org.jetbrains.kotlin.resolve.descriptorUtil.AnnotationsForResolveUtilsKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.error.ErrorUtils;

/* JADX INFO: compiled from: OverloadChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u0010B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u000eH\u0002J\u0016\u0010\u000f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/resolve/OverloadChecker;", "", "specificityComparator", "Lorg/jetbrains/kotlin/resolve/calls/results/TypeSpecificityComparator;", "(Lorg/jetbrains/kotlin/resolve/calls/results/TypeSpecificityComparator;)V", "getSpecificityComparator", "()Lorg/jetbrains/kotlin/resolve/calls/results/TypeSpecificityComparator;", "checkOverloadability", "", "a", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "getDeclarationCategory", "Lorg/jetbrains/kotlin/resolve/OverloadChecker$DeclarationCategory;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "isOverloadable", "DeclarationCategory", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class OverloadChecker {
    private final TypeSpecificityComparator specificityComparator;

    /* JADX INFO: compiled from: OverloadChecker.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/resolve/OverloadChecker$DeclarationCategory;", "", "(Ljava/lang/String;I)V", "TYPE_OR_VALUE", "FUNCTION", "EXTENSION_PROPERTY", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private enum DeclarationCategory {
        TYPE_OR_VALUE,
        FUNCTION,
        EXTENSION_PROPERTY;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<DeclarationCategory> getEntries() {
            return $ENTRIES;
        }
    }

    public OverloadChecker(TypeSpecificityComparator specificityComparator) {
        Intrinsics.checkNotNullParameter(specificityComparator, "specificityComparator");
        this.specificityComparator = specificityComparator;
    }

    public final TypeSpecificityComparator getSpecificityComparator() {
        return this.specificityComparator;
    }

    public final boolean isOverloadable(DeclarationDescriptor a, DeclarationDescriptor b) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        if (getDeclarationCategory(a) != getDeclarationCategory(b)) {
            return true;
        }
        if ((a instanceof CallableDescriptor) && (b instanceof CallableDescriptor)) {
            return checkOverloadability((CallableDescriptor) a, (CallableDescriptor) b);
        }
        return false;
    }

    private final boolean checkOverloadability(CallableDescriptor a, CallableDescriptor b) {
        if (AnnotationsForResolveUtilsKt.hasLowPriorityInOverloadResolution(a) != AnnotationsForResolveUtilsKt.hasLowPriorityInOverloadResolution(b) || a.getTypeParameters().isEmpty() != b.getTypeParameters().isEmpty()) {
            return true;
        }
        if (((a instanceof FunctionDescriptor) && ErrorUtils.INSTANCE.containsErrorTypeInParameters((FunctionDescriptor) a)) || (((b instanceof FunctionDescriptor) && ErrorUtils.INSTANCE.containsErrorTypeInParameters((FunctionDescriptor) b)) || DescriptorUtilsKt.varargParameterPosition(a) != DescriptorUtilsKt.varargParameterPosition(b))) {
            return true;
        }
        FlatSignature flatSignatureCreateFromCallableDescriptor = FlatSignatureUtilsKt.createFromCallableDescriptor(FlatSignature.INSTANCE, a);
        FlatSignature flatSignatureCreateFromCallableDescriptor2 = FlatSignatureUtilsKt.createFromCallableDescriptor(FlatSignature.INSTANCE, b);
        return (FlatSignatureKt.isSignatureNotLessSpecific$default(ConstraintSystemBuilderImpl.INSTANCE.forSpecificity(), flatSignatureCreateFromCallableDescriptor, flatSignatureCreateFromCallableDescriptor2, OverloadabilitySpecificityCallbacks.INSTANCE, this.specificityComparator, false, 16, null) && FlatSignatureKt.isSignatureNotLessSpecific$default(ConstraintSystemBuilderImpl.INSTANCE.forSpecificity(), flatSignatureCreateFromCallableDescriptor2, flatSignatureCreateFromCallableDescriptor, OverloadabilitySpecificityCallbacks.INSTANCE, this.specificityComparator, false, 16, null)) ? false : true;
    }

    private final DeclarationCategory getDeclarationCategory(DeclarationDescriptor a) {
        if (a instanceof PropertyDescriptor) {
            if (DescriptorUtilsKt.isExtensionProperty(a)) {
                return DeclarationCategory.EXTENSION_PROPERTY;
            }
            return DeclarationCategory.TYPE_OR_VALUE;
        }
        if (a instanceof FunctionDescriptor) {
            return DeclarationCategory.FUNCTION;
        }
        if (a instanceof ClassifierDescriptor) {
            return DeclarationCategory.TYPE_OR_VALUE;
        }
        throw new IllegalStateException(("Unexpected declaration kind: " + a).toString());
    }
}
