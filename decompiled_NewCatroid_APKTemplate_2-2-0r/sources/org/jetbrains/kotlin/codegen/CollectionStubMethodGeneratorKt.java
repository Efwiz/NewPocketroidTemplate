package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.jvm.JavaToKotlinClassMapper;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;
import org.jetbrains.kotlin.types.checker.KotlinTypeCheckerImpl;

/* JADX INFO: compiled from: CollectionStubMethodGenerator.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0002"}, d2 = {"READ_ONLY_ARE_EQUAL_TO_MUTABLE_TYPE_CHECKER", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeChecker;", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CollectionStubMethodGeneratorKt {
    private static final KotlinTypeChecker READ_ONLY_ARE_EQUAL_TO_MUTABLE_TYPE_CHECKER;

    static {
        KotlinTypeChecker kotlinTypeCheckerWithAxioms = KotlinTypeCheckerImpl.withAxioms(new KotlinTypeChecker.TypeConstructorEquality() { // from class: org.jetbrains.kotlin.codegen.CollectionStubMethodGeneratorKt$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.types.checker.KotlinTypeChecker.TypeConstructorEquality
            public final boolean equals(TypeConstructor typeConstructor, TypeConstructor typeConstructor2) {
                return CollectionStubMethodGeneratorKt.READ_ONLY_ARE_EQUAL_TO_MUTABLE_TYPE_CHECKER$lambda$0(typeConstructor, typeConstructor2);
            }
        });
        Intrinsics.checkNotNullExpressionValue(kotlinTypeCheckerWithAxioms, "withAxioms(...)");
        READ_ONLY_ARE_EQUAL_TO_MUTABLE_TYPE_CHECKER = kotlinTypeCheckerWithAxioms;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean READ_ONLY_ARE_EQUAL_TO_MUTABLE_TYPE_CHECKER$lambda$0(TypeConstructor x, TypeConstructor y) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(y, "y");
        ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = x.mo7434getDeclarationDescriptor();
        ClassDescriptor classDescriptorConvertMutableToReadOnly = classifierDescriptorMo7434getDeclarationDescriptor instanceof ClassDescriptor ? (ClassDescriptor) classifierDescriptorMo7434getDeclarationDescriptor : null;
        if (classDescriptorConvertMutableToReadOnly == null) {
            return Intrinsics.areEqual(x, y);
        }
        ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor2 = y.mo7434getDeclarationDescriptor();
        ClassDescriptor classDescriptorConvertMutableToReadOnly2 = classifierDescriptorMo7434getDeclarationDescriptor2 instanceof ClassDescriptor ? (ClassDescriptor) classifierDescriptorMo7434getDeclarationDescriptor2 : null;
        if (classDescriptorConvertMutableToReadOnly2 == null) {
            return Intrinsics.areEqual(x, y);
        }
        JavaToKotlinClassMapper javaToKotlinClassMapper = JavaToKotlinClassMapper.INSTANCE;
        if (javaToKotlinClassMapper.isMutable(classDescriptorConvertMutableToReadOnly)) {
            classDescriptorConvertMutableToReadOnly = javaToKotlinClassMapper.convertMutableToReadOnly(classDescriptorConvertMutableToReadOnly);
        }
        if (javaToKotlinClassMapper.isMutable(classDescriptorConvertMutableToReadOnly2)) {
            classDescriptorConvertMutableToReadOnly2 = javaToKotlinClassMapper.convertMutableToReadOnly(classDescriptorConvertMutableToReadOnly2);
        }
        return Intrinsics.areEqual(classDescriptorConvertMutableToReadOnly.getTypeConstructor(), classDescriptorConvertMutableToReadOnly2.getTypeConstructor());
    }
}
