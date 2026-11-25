package org.jetbrains.kotlin.resolve.jvm.diagnostics;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;

/* JADX INFO: compiled from: ConflictingJvmDeclarationsData.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u000e\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\t\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\t¢\u0006\u0002\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\t¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0019\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0014¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/ConflictingJvmDeclarationsData;", "", "classInternalName", "", "classOrigin", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;", "signature", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/RawSignature;", "signatureOrigins", "", "signatureDescriptors", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/RawSignature;Ljava/util/Collection;Ljava/util/Collection;)V", "getClassInternalName", "()Ljava/lang/String;", "getClassOrigin", "()Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;", "getSignature", "()Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/RawSignature;", "getSignatureDescriptors", "()Ljava/util/Collection;", "getSignatureOrigins", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConflictingJvmDeclarationsData {
    private final String classInternalName;
    private final JvmDeclarationOrigin classOrigin;
    private final RawSignature signature;
    private final Collection<DeclarationDescriptor> signatureDescriptors;
    private final Collection<JvmDeclarationOrigin> signatureOrigins;

    /* JADX WARN: Multi-variable type inference failed */
    public ConflictingJvmDeclarationsData(String classInternalName, JvmDeclarationOrigin jvmDeclarationOrigin, RawSignature signature, Collection<? extends JvmDeclarationOrigin> collection, Collection<? extends DeclarationDescriptor> signatureDescriptors) {
        Intrinsics.checkNotNullParameter(classInternalName, "classInternalName");
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(signatureDescriptors, "signatureDescriptors");
        this.classInternalName = classInternalName;
        this.classOrigin = jvmDeclarationOrigin;
        this.signature = signature;
        this.signatureOrigins = collection;
        this.signatureDescriptors = signatureDescriptors;
    }

    public final String getClassInternalName() {
        return this.classInternalName;
    }

    public final JvmDeclarationOrigin getClassOrigin() {
        return this.classOrigin;
    }

    public final RawSignature getSignature() {
        return this.signature;
    }

    public final Collection<JvmDeclarationOrigin> getSignatureOrigins() {
        return this.signatureOrigins;
    }

    public final Collection<DeclarationDescriptor> getSignatureDescriptors() {
        return this.signatureDescriptors;
    }
}
