package org.jetbrains.kotlin.ir.backend.js.export;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: ExportModel.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u0007\b\u0004¢\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u0082\u0001\u0007\b\t\n\u000b\f\r\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedDeclaration;", "", "()V", "attributes", "", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedAttribute;", "getAttributes", "()Ljava/util/List;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ErrorDeclaration;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedClass;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedConstructSignature;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedConstructor;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedFunction;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedNamespace;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedProperty;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ExportedDeclaration {
    private final List<ExportedAttribute> attributes;

    public /* synthetic */ ExportedDeclaration(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private ExportedDeclaration() {
        this.attributes = new ArrayList();
    }

    public final List<ExportedAttribute> getAttributes() {
        return this.attributes;
    }
}
