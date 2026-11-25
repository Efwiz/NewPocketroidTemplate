package org.jetbrains.kotlin.serialization.js;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.js.JsProtoBuf;
import org.jetbrains.kotlin.utils.JsMetadataVersion;

/* JADX INFO: compiled from: KotlinJavascriptSerializationUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B9\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0005\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\t\u0010\u0017\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J\t\u0010\u0019\u001a\u00020\bHÆ\u0003J\u000f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\n0\u0005HÆ\u0003J\t\u0010\u001b\u001a\u00020\fHÆ\u0003JG\u0010\u001c\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u00052\b\b\u0002\u0010\u000b\u001a\u00020\fHÆ\u0001J\u0013\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010 \u001a\u00020!HÖ\u0001J\t\u0010\"\u001a\u00020\nHÖ\u0001R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000fR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/serialization/js/KotlinJavaScriptLibraryParts;", "", "header", "Lorg/jetbrains/kotlin/metadata/js/JsProtoBuf$Header;", "body", "", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/serialization/js/ModuleKind;", "importedModules", "", "metadataVersion", "Lorg/jetbrains/kotlin/utils/JsMetadataVersion;", "(Lorg/jetbrains/kotlin/metadata/js/JsProtoBuf$Header;Ljava/util/List;Lorg/jetbrains/kotlin/serialization/js/ModuleKind;Ljava/util/List;Lorg/jetbrains/kotlin/utils/JsMetadataVersion;)V", "getBody", "()Ljava/util/List;", "getHeader", "()Lorg/jetbrains/kotlin/metadata/js/JsProtoBuf$Header;", "getImportedModules", "getKind", "()Lorg/jetbrains/kotlin/serialization/js/ModuleKind;", "getMetadataVersion", "()Lorg/jetbrains/kotlin/utils/JsMetadataVersion;", "component1", "component2", "component3", "component4", "component5", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "js.serializer"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class KotlinJavaScriptLibraryParts {
    private final List<ProtoBuf.PackageFragment> body;
    private final JsProtoBuf.Header header;
    private final List<String> importedModules;
    private final ModuleKind kind;
    private final JsMetadataVersion metadataVersion;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ KotlinJavaScriptLibraryParts copy$default(KotlinJavaScriptLibraryParts kotlinJavaScriptLibraryParts, JsProtoBuf.Header header, List list, ModuleKind moduleKind, List list2, JsMetadataVersion jsMetadataVersion, int i, Object obj) {
        if ((i & 1) != 0) {
            header = kotlinJavaScriptLibraryParts.header;
        }
        if ((i & 2) != 0) {
            list = kotlinJavaScriptLibraryParts.body;
        }
        List list3 = list;
        if ((i & 4) != 0) {
            moduleKind = kotlinJavaScriptLibraryParts.kind;
        }
        ModuleKind moduleKind2 = moduleKind;
        if ((i & 8) != 0) {
            list2 = kotlinJavaScriptLibraryParts.importedModules;
        }
        List list4 = list2;
        if ((i & 16) != 0) {
            jsMetadataVersion = kotlinJavaScriptLibraryParts.metadataVersion;
        }
        return kotlinJavaScriptLibraryParts.copy(header, list3, moduleKind2, list4, jsMetadataVersion);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final JsProtoBuf.Header getHeader() {
        return this.header;
    }

    public final List<ProtoBuf.PackageFragment> component2() {
        return this.body;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final ModuleKind getKind() {
        return this.kind;
    }

    public final List<String> component4() {
        return this.importedModules;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final JsMetadataVersion getMetadataVersion() {
        return this.metadataVersion;
    }

    public final KotlinJavaScriptLibraryParts copy(JsProtoBuf.Header header, List<ProtoBuf.PackageFragment> body, ModuleKind kind, List<String> importedModules, JsMetadataVersion metadataVersion) {
        Intrinsics.checkNotNullParameter(header, "header");
        Intrinsics.checkNotNullParameter(body, "body");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(importedModules, "importedModules");
        Intrinsics.checkNotNullParameter(metadataVersion, "metadataVersion");
        return new KotlinJavaScriptLibraryParts(header, body, kind, importedModules, metadataVersion);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof KotlinJavaScriptLibraryParts)) {
            return false;
        }
        KotlinJavaScriptLibraryParts kotlinJavaScriptLibraryParts = (KotlinJavaScriptLibraryParts) other;
        return Intrinsics.areEqual(this.header, kotlinJavaScriptLibraryParts.header) && Intrinsics.areEqual(this.body, kotlinJavaScriptLibraryParts.body) && this.kind == kotlinJavaScriptLibraryParts.kind && Intrinsics.areEqual(this.importedModules, kotlinJavaScriptLibraryParts.importedModules) && Intrinsics.areEqual(this.metadataVersion, kotlinJavaScriptLibraryParts.metadataVersion);
    }

    public int hashCode() {
        return (((((((this.header.hashCode() * 31) + this.body.hashCode()) * 31) + this.kind.hashCode()) * 31) + this.importedModules.hashCode()) * 31) + this.metadataVersion.hashCode();
    }

    public String toString() {
        return "KotlinJavaScriptLibraryParts(header=" + this.header + ", body=" + this.body + ", kind=" + this.kind + ", importedModules=" + this.importedModules + ", metadataVersion=" + this.metadataVersion + ')';
    }

    public KotlinJavaScriptLibraryParts(JsProtoBuf.Header header, List<ProtoBuf.PackageFragment> body, ModuleKind kind, List<String> importedModules, JsMetadataVersion metadataVersion) {
        Intrinsics.checkNotNullParameter(header, "header");
        Intrinsics.checkNotNullParameter(body, "body");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(importedModules, "importedModules");
        Intrinsics.checkNotNullParameter(metadataVersion, "metadataVersion");
        this.header = header;
        this.body = body;
        this.kind = kind;
        this.importedModules = importedModules;
        this.metadataVersion = metadataVersion;
    }

    public final JsProtoBuf.Header getHeader() {
        return this.header;
    }

    public final List<ProtoBuf.PackageFragment> getBody() {
        return this.body;
    }

    public final ModuleKind getKind() {
        return this.kind;
    }

    public final List<String> getImportedModules() {
        return this.importedModules;
    }

    public final JsMetadataVersion getMetadataVersion() {
        return this.metadataVersion;
    }
}
