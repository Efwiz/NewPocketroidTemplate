package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: CapturedParamDesc.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\t\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u0011\u0010\n\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u000eR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/CapturedParamDesc;", "", "containingLambdaType", "Lorg/jetbrains/org/objectweb/asm/Type;", "fieldName", "", ModuleXmlParser.TYPE, "isSuspend", "", "(Lorg/jetbrains/org/objectweb/asm/Type;Ljava/lang/String;Lorg/jetbrains/org/objectweb/asm/Type;Z)V", "containingLambdaName", "getContainingLambdaName", "()Ljava/lang/String;", "getFieldName", "()Z", "getType", "()Lorg/jetbrains/org/objectweb/asm/Type;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CapturedParamDesc {
    private final String containingLambdaName;
    private final String fieldName;
    private final boolean isSuspend;
    private final Type type;

    public CapturedParamDesc(Type containingLambdaType, String fieldName, Type type, boolean z) {
        Intrinsics.checkNotNullParameter(containingLambdaType, "containingLambdaType");
        Intrinsics.checkNotNullParameter(fieldName, "fieldName");
        Intrinsics.checkNotNullParameter(type, "type");
        this.fieldName = fieldName;
        this.type = type;
        this.isSuspend = z;
        String internalName = containingLambdaType.getInternalName();
        Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
        this.containingLambdaName = internalName;
    }

    public /* synthetic */ CapturedParamDesc(Type type, String str, Type type2, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(type, str, type2, (i & 8) != 0 ? false : z);
    }

    public final String getFieldName() {
        return this.fieldName;
    }

    public final Type getType() {
        return this.type;
    }

    /* JADX INFO: renamed from: isSuspend, reason: from getter */
    public final boolean getIsSuspend() {
        return this.isSuspend;
    }

    public final String getContainingLambdaName() {
        return this.containingLambdaName;
    }
}
