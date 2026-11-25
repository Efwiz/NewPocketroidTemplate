package org.jetbrains.kotlin.serialization.deserialization;

import java.util.List;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;

/* JADX INFO: loaded from: classes6.dex */
public interface ErrorReporter {
    public static final ErrorReporter DO_NOTHING = new ErrorReporter() { // from class: org.jetbrains.kotlin.serialization.deserialization.ErrorReporter.1
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i != 1) {
                objArr[0] = "descriptor";
            } else {
                objArr[0] = "unresolvedSuperClasses";
            }
            objArr[1] = "org/jetbrains/kotlin/serialization/deserialization/ErrorReporter$1";
            if (i != 2) {
                objArr[2] = "reportIncompleteHierarchy";
            } else {
                objArr[2] = "reportCannotInferVisibility";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        @Override // org.jetbrains.kotlin.serialization.deserialization.ErrorReporter
        public void reportCannotInferVisibility(CallableMemberDescriptor callableMemberDescriptor) {
            if (callableMemberDescriptor == null) {
                $$$reportNull$$$0(2);
            }
        }

        @Override // org.jetbrains.kotlin.serialization.deserialization.ErrorReporter
        public void reportIncompleteHierarchy(ClassDescriptor classDescriptor, List<String> list) {
            if (classDescriptor == null) {
                $$$reportNull$$$0(0);
            }
            if (list == null) {
                $$$reportNull$$$0(1);
            }
        }
    };

    void reportCannotInferVisibility(CallableMemberDescriptor callableMemberDescriptor);

    void reportIncompleteHierarchy(ClassDescriptor classDescriptor, List<String> list);
}
