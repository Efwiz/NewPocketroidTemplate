package org.jetbrains.kotlin.ir.backend.js.export;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: ExportModel.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedParameter;", "", "name", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;", "hasDefaultValue", "", "(Ljava/lang/String;Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;Z)V", "getHasDefaultValue", "()Z", "getName", "()Ljava/lang/String;", "getType", "()Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExportedParameter {
    private final boolean hasDefaultValue;
    private final String name;
    private final ExportedType type;

    public ExportedParameter(String name2, ExportedType type, boolean z) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(type, "type");
        this.name = name2;
        this.type = type;
        this.hasDefaultValue = z;
    }

    public /* synthetic */ ExportedParameter(String str, ExportedType exportedType, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, exportedType, (i & 4) != 0 ? false : z);
    }

    public final String getName() {
        return this.name;
    }

    public final ExportedType getType() {
        return this.type;
    }

    public final boolean getHasDefaultValue() {
        return this.hasDefaultValue;
    }
}
