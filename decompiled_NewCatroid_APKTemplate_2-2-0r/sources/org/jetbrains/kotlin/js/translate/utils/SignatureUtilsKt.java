package org.jetbrains.kotlin.js.translate.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.VariableAccessorDescriptor;
import org.jetbrains.kotlin.js.naming.EncodeSignatureKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;

/* JADX INFO: compiled from: signatureUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0002\u001a\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0004\u001a\u00020\u0005¨\u0006\u0006"}, d2 = {"escape", "", "s", "generateSignature", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SignatureUtilsKt {
    public static final String generateSignature(DeclarationDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (DescriptorUtils.isDescriptorWithLocalVisibility(descriptor)) {
            return null;
        }
        if ((descriptor instanceof DeclarationDescriptorWithVisibility) && Intrinsics.areEqual(((DeclarationDescriptorWithVisibility) descriptor).getVisibilityImpl(), DescriptorVisibilities.PRIVATE) && !AnnotationsUtils.isNativeObject(descriptor) && !AnnotationsUtils.isLibraryObject(descriptor)) {
            return null;
        }
        if (descriptor instanceof CallableDescriptor) {
            boolean z = descriptor instanceof ConstructorDescriptor;
            if (z) {
                ConstructorDescriptor constructorDescriptor = (ConstructorDescriptor) descriptor;
                if (constructorDescriptor.isPrimary()) {
                    ClassDescriptor constructedClass = constructorDescriptor.getConstructedClass();
                    Intrinsics.checkNotNullExpressionValue(constructedClass, "getConstructedClass(...)");
                    return generateSignature(constructedClass);
                }
            }
            CallableDescriptor callableDescriptor = (CallableDescriptor) descriptor;
            DeclarationDescriptor containingDeclaration = callableDescriptor.getDeclarationDescriptor();
            Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
            String strGenerateSignature = generateSignature(containingDeclaration);
            if (strGenerateSignature == null) {
                return null;
            }
            if (!(descriptor instanceof VariableAccessorDescriptor) && !z && callableDescriptor.getShortName().isSpecial()) {
                return null;
            }
            StringBuilder sbAppend = new StringBuilder().append(strGenerateSignature).append(descriptor instanceof FunctionDescriptor ? "#" : "!");
            String strAsString = callableDescriptor.getShortName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            return sbAppend.append(escape(strAsString)).append('|').append(EncodeSignatureKt.encodeSignature(callableDescriptor)).toString();
        }
        if (descriptor instanceof PackageFragmentDescriptor) {
            String strAsString2 = DescriptorUtilsKt.getModule(descriptor).getShortName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
            Sequence sequenceSequenceOf = SequencesKt.sequenceOf(strAsString2);
            List<Name> listPathSegments = ((PackageFragmentDescriptor) descriptor).getFqName().pathSegments();
            Intrinsics.checkNotNullExpressionValue(listPathSegments, "pathSegments(...)");
            List<Name> list = listPathSegments;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                String identifier = ((Name) it.next()).getIdentifier();
                Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
                arrayList.add(identifier);
            }
            return SequencesKt.joinToString$default(SequencesKt.plus(sequenceSequenceOf, (Iterable) arrayList), ".", null, null, 0, null, new Function1<String, CharSequence>() { // from class: org.jetbrains.kotlin.js.translate.utils.SignatureUtilsKt.generateSignature.1
                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(String it2) {
                    Intrinsics.checkNotNullParameter(it2, "it");
                    return SignatureUtilsKt.escape(it2);
                }
            }, 30, null);
        }
        if (!(descriptor instanceof ClassDescriptor)) {
            return null;
        }
        ClassDescriptor classDescriptor = (ClassDescriptor) descriptor;
        DeclarationDescriptor declarationDescriptor = classDescriptor.getDeclarationDescriptor();
        Intrinsics.checkNotNullExpressionValue(declarationDescriptor, "getContainingDeclaration(...)");
        String strGenerateSignature2 = generateSignature(declarationDescriptor);
        if (strGenerateSignature2 == null || classDescriptor.getShortName().isSpecial()) {
            return null;
        }
        StringBuilder sbAppend2 = new StringBuilder().append(strGenerateSignature2).append('$');
        String strAsString3 = classDescriptor.getShortName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString3, "asString(...)");
        return sbAppend2.append(escape(strAsString3)).toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String escape(String str) {
        String strValueOf;
        StringBuilder sb = new StringBuilder();
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\\' || cCharAt == '\"' || cCharAt == '.' || cCharAt == '$' || cCharAt == '#' || cCharAt == '!' || cCharAt == '<' || cCharAt == '>' || cCharAt == '|' || cCharAt == '+' || cCharAt == '-' || cCharAt == ':' || cCharAt == '*' || cCharAt == '?') {
                strValueOf = "\\" + cCharAt;
            } else {
                strValueOf = String.valueOf(cCharAt);
            }
            sb.append(strValueOf);
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
