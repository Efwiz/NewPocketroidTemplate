package org.jetbrains.kotlin.library.abi;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: LibraryAbiRenderer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0007\u0018\u00002\u00020\u0001BG\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\u001a\b\u0002\u0010\t\u001a\u0014\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\b0\n¢\u0006\u0002\u0010\fR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R#\u0010\t\u001a\u0014\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\b0\n¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/AbiRenderingSettings;", "", "renderedSignatureVersion", "Lorg/jetbrains/kotlin/library/abi/AbiSignatureVersion;", "renderManifest", "", "renderDeclarations", "indentationString", "", "whenSignatureNotFound", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/library/abi/AbiDeclaration;", "(Lorg/jetbrains/kotlin/library/abi/AbiSignatureVersion;ZZLjava/lang/String;Lkotlin/jvm/functions/Function2;)V", "getIndentationString", "()Ljava/lang/String;", "getRenderDeclarations", "()Z", "getRenderManifest", "getRenderedSignatureVersion", "()Lorg/jetbrains/kotlin/library/abi/AbiSignatureVersion;", "getWhenSignatureNotFound", "()Lkotlin/jvm/functions/Function2;", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
@ExperimentalLibraryAbiReader
public final class AbiRenderingSettings {
    private final String indentationString;
    private final boolean renderDeclarations;
    private final boolean renderManifest;
    private final AbiSignatureVersion renderedSignatureVersion;
    private final Function2<AbiDeclaration, AbiSignatureVersion, String> whenSignatureNotFound;

    /* JADX WARN: Multi-variable type inference failed */
    public AbiRenderingSettings(AbiSignatureVersion renderedSignatureVersion, boolean z, boolean z2, String indentationString, Function2<? super AbiDeclaration, ? super AbiSignatureVersion, String> whenSignatureNotFound) {
        Intrinsics.checkNotNullParameter(renderedSignatureVersion, "renderedSignatureVersion");
        Intrinsics.checkNotNullParameter(indentationString, "indentationString");
        Intrinsics.checkNotNullParameter(whenSignatureNotFound, "whenSignatureNotFound");
        this.renderedSignatureVersion = renderedSignatureVersion;
        this.renderManifest = z;
        this.renderDeclarations = z2;
        this.indentationString = indentationString;
        this.whenSignatureNotFound = whenSignatureNotFound;
    }

    public final AbiSignatureVersion getRenderedSignatureVersion() {
        return this.renderedSignatureVersion;
    }

    public final boolean getRenderManifest() {
        return this.renderManifest;
    }

    public final boolean getRenderDeclarations() {
        return this.renderDeclarations;
    }

    public /* synthetic */ AbiRenderingSettings(AbiSignatureVersion abiSignatureVersion, boolean z, boolean z2, String str, AnonymousClass1 anonymousClass1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(abiSignatureVersion, (i & 2) != 0 ? false : z, (i & 4) != 0 ? true : z2, (i & 8) != 0 ? "    " : str, (i & 16) != 0 ? new Function2() { // from class: org.jetbrains.kotlin.library.abi.AbiRenderingSettings.1
            @Override // kotlin.jvm.functions.Function2
            public final Void invoke(AbiDeclaration declaration, AbiSignatureVersion signatureVersion) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                Intrinsics.checkNotNullParameter(signatureVersion, "signatureVersion");
                throw new IllegalStateException(("No signature " + signatureVersion + " for " + declaration.getClass() + ", " + declaration).toString());
            }
        } : anonymousClass1);
    }

    public final String getIndentationString() {
        return this.indentationString;
    }

    public final Function2<AbiDeclaration, AbiSignatureVersion, String> getWhenSignatureNotFound() {
        return this.whenSignatureNotFound;
    }
}
