package org.jetbrains.kotlin.resolve.checkers;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtParameterList;
import org.jetbrains.kotlin.psi.KtPrimaryConstructor;

/* JADX INFO: compiled from: DataClassDeclarationChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/DataClassDeclarationChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DataClassDeclarationChecker implements DeclarationChecker {
    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        List<KtParameter> listEmptyList;
        KtParameterList valueParameterList;
        PsiElement nameIdentifier;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if ((descriptor instanceof ClassDescriptor) && (declaration instanceof KtClassOrObject)) {
            ClassDescriptor classDescriptor = (ClassDescriptor) descriptor;
            if (classDescriptor.isData() && classDescriptor.getKind() == ClassKind.CLASS) {
                if (classDescriptor.mo6347getUnsubstitutedPrimaryConstructor() == null) {
                    Collection<ClassConstructorDescriptor> constructors = classDescriptor.getConstructors();
                    Intrinsics.checkNotNullExpressionValue(constructors, "getConstructors(...)");
                    if (!constructors.isEmpty() && (nameIdentifier = ((KtClassOrObject) declaration).mo7392getNameIdentifier()) != null) {
                        context.getTrace().report(Errors.PRIMARY_CONSTRUCTOR_REQUIRED_FOR_DATA_CLASS.on(nameIdentifier));
                    }
                }
                KtClassOrObject ktClassOrObject = (KtClassOrObject) declaration;
                KtPrimaryConstructor primaryConstructor = ktClassOrObject.getPrimaryConstructor();
                if (primaryConstructor == null || (listEmptyList = primaryConstructor.getValueParameters()) == null) {
                    listEmptyList = CollectionsKt.emptyList();
                }
                if (listEmptyList.isEmpty()) {
                    KtParameterList nameIdentifier2 = (primaryConstructor == null || (valueParameterList = primaryConstructor.getValueParameterList()) == null) ? ktClassOrObject.mo7392getNameIdentifier() : valueParameterList;
                    if (nameIdentifier2 != null) {
                        context.getTrace().report(Errors.DATA_CLASS_WITHOUT_PARAMETERS.on(nameIdentifier2));
                    }
                }
                for (KtParameter ktParameter : listEmptyList) {
                    if (ktParameter.isVarArg()) {
                        context.getTrace().report(Errors.DATA_CLASS_VARARG_PARAMETER.on(ktParameter));
                    }
                    if (!ktParameter.hasValOrVar()) {
                        context.getTrace().report(Errors.DATA_CLASS_NOT_PROPERTY_PARAMETER.on(ktParameter));
                    }
                }
            }
        }
    }
}
