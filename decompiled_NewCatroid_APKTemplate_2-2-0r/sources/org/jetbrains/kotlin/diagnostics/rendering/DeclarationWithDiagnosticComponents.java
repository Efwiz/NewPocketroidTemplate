package org.jetbrains.kotlin.diagnostics.rendering;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.sequences.SequencesKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.platform.PlatformSpecificDiagnosticComponents;

/* JADX INFO: compiled from: AnnotationsWhitelistDescriptorRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010(\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\u0004HÆ\u0003J\t\u0010\r\u001a\u00020\u0006HÆ\u0003J\u001d\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0002HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\u000f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00020\u0015H\u0096\u0002J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/rendering/DeclarationWithDiagnosticComponents;", "", "", "declaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "diagnosticComponents", "Lorg/jetbrains/kotlin/platform/PlatformSpecificDiagnosticComponents;", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;Lorg/jetbrains/kotlin/platform/PlatformSpecificDiagnosticComponents;)V", "getDeclaration", "()Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getDiagnosticComponents", "()Lorg/jetbrains/kotlin/platform/PlatformSpecificDiagnosticComponents;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "iterator", "", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class DeclarationWithDiagnosticComponents implements Iterable<Object>, KMappedMarker {
    private final DeclarationDescriptor declaration;
    private final PlatformSpecificDiagnosticComponents diagnosticComponents;

    public static /* synthetic */ DeclarationWithDiagnosticComponents copy$default(DeclarationWithDiagnosticComponents declarationWithDiagnosticComponents, DeclarationDescriptor declarationDescriptor, PlatformSpecificDiagnosticComponents platformSpecificDiagnosticComponents, int i, Object obj) {
        if ((i & 1) != 0) {
            declarationDescriptor = declarationWithDiagnosticComponents.declaration;
        }
        if ((i & 2) != 0) {
            platformSpecificDiagnosticComponents = declarationWithDiagnosticComponents.diagnosticComponents;
        }
        return declarationWithDiagnosticComponents.copy(declarationDescriptor, platformSpecificDiagnosticComponents);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final DeclarationDescriptor getDeclaration() {
        return this.declaration;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final PlatformSpecificDiagnosticComponents getDiagnosticComponents() {
        return this.diagnosticComponents;
    }

    public final DeclarationWithDiagnosticComponents copy(DeclarationDescriptor declaration, PlatformSpecificDiagnosticComponents diagnosticComponents) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(diagnosticComponents, "diagnosticComponents");
        return new DeclarationWithDiagnosticComponents(declaration, diagnosticComponents);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DeclarationWithDiagnosticComponents)) {
            return false;
        }
        DeclarationWithDiagnosticComponents declarationWithDiagnosticComponents = (DeclarationWithDiagnosticComponents) other;
        return Intrinsics.areEqual(this.declaration, declarationWithDiagnosticComponents.declaration) && Intrinsics.areEqual(this.diagnosticComponents, declarationWithDiagnosticComponents.diagnosticComponents);
    }

    public int hashCode() {
        return (this.declaration.hashCode() * 31) + this.diagnosticComponents.hashCode();
    }

    public String toString() {
        return "DeclarationWithDiagnosticComponents(declaration=" + this.declaration + ", diagnosticComponents=" + this.diagnosticComponents + ')';
    }

    public DeclarationWithDiagnosticComponents(DeclarationDescriptor declaration, PlatformSpecificDiagnosticComponents diagnosticComponents) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(diagnosticComponents, "diagnosticComponents");
        this.declaration = declaration;
        this.diagnosticComponents = diagnosticComponents;
    }

    public final DeclarationDescriptor getDeclaration() {
        return this.declaration;
    }

    public final PlatformSpecificDiagnosticComponents getDiagnosticComponents() {
        return this.diagnosticComponents;
    }

    @Override // java.lang.Iterable
    public Iterator<Object> iterator() {
        return SequencesKt.sequenceOf(this.declaration, this.diagnosticComponents).iterator();
    }
}
