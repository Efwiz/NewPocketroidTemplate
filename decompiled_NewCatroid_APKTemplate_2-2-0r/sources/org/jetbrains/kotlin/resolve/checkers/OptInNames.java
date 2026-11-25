package org.jetbrains.kotlin.resolve.checkers;

import io.netty.util.internal.StringUtil;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: OptInNames.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u000e\n\u0002\b\b\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u001e2\u0006\u0010 \u001a\u00020\u001eJ\u000e\u0010!\u001a\u00020\u001e2\u0006\u0010\"\u001a\u00020\u001eJ(\u0010#\u001a\u00020\u001e2\u0006\u0010$\u001a\u00020\u001e2\b\u0010%\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\"\u001a\u00020\u001e2\u0006\u0010 \u001a\u00020\u001eR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\nR\u0011\u0010\u0011\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000eR\u0011\u0010\u0013\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\nR\u0011\u0010\u0015\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u000eR\u0011\u0010\u0017\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0006R\u0011\u0010\u0019\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\nR\u0011\u0010\u001b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u000e¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/OptInNames;", "", "()V", "OPT_IN_ANNOTATION_CLASS", "Lorg/jetbrains/kotlin/name/Name;", "getOPT_IN_ANNOTATION_CLASS", "()Lorg/jetbrains/kotlin/name/Name;", "OPT_IN_CLASS_ID", "Lorg/jetbrains/kotlin/name/ClassId;", "getOPT_IN_CLASS_ID", "()Lorg/jetbrains/kotlin/name/ClassId;", "OPT_IN_FQ_NAME", "Lorg/jetbrains/kotlin/name/FqName;", "getOPT_IN_FQ_NAME", "()Lorg/jetbrains/kotlin/name/FqName;", "REQUIRES_OPT_IN_CLASS_ID", "getREQUIRES_OPT_IN_CLASS_ID", "REQUIRES_OPT_IN_FQ_NAME", "getREQUIRES_OPT_IN_FQ_NAME", "SUBCLASS_OPT_IN_REQUIRED_CLASS_ID", "getSUBCLASS_OPT_IN_REQUIRED_CLASS_ID", "SUBCLASS_OPT_IN_REQUIRED_FQ_NAME", "getSUBCLASS_OPT_IN_REQUIRED_FQ_NAME", "WAS_EXPERIMENTAL_ANNOTATION_CLASS", "getWAS_EXPERIMENTAL_ANNOTATION_CLASS", "WAS_EXPERIMENTAL_CLASS_ID", "getWAS_EXPERIMENTAL_CLASS_ID", "WAS_EXPERIMENTAL_FQ_NAME", "getWAS_EXPERIMENTAL_FQ_NAME", "buildDefaultDiagnosticMessage", "", "prefix", "markerName", "buildMessagePrefix", "verb", "buildOverrideMessage", "supertypeName", "markerMessage", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class OptInNames {
    public static final OptInNames INSTANCE = new OptInNames();
    private static final Name OPT_IN_ANNOTATION_CLASS;
    private static final ClassId OPT_IN_CLASS_ID;
    private static final FqName OPT_IN_FQ_NAME;
    private static final ClassId REQUIRES_OPT_IN_CLASS_ID;
    private static final FqName REQUIRES_OPT_IN_FQ_NAME;
    private static final ClassId SUBCLASS_OPT_IN_REQUIRED_CLASS_ID;
    private static final FqName SUBCLASS_OPT_IN_REQUIRED_FQ_NAME;
    private static final Name WAS_EXPERIMENTAL_ANNOTATION_CLASS;
    private static final ClassId WAS_EXPERIMENTAL_CLASS_ID;
    private static final FqName WAS_EXPERIMENTAL_FQ_NAME;

    private OptInNames() {
    }

    static {
        FqName fqName = new FqName("kotlin.RequiresOptIn");
        REQUIRES_OPT_IN_FQ_NAME = fqName;
        ClassId classId = ClassId.topLevel(fqName);
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        REQUIRES_OPT_IN_CLASS_ID = classId;
        FqName fqName2 = new FqName("kotlin.OptIn");
        OPT_IN_FQ_NAME = fqName2;
        ClassId classId2 = ClassId.topLevel(fqName2);
        Intrinsics.checkNotNullExpressionValue(classId2, "topLevel(...)");
        OPT_IN_CLASS_ID = classId2;
        FqName fqName3 = new FqName("kotlin.SubclassOptInRequired");
        SUBCLASS_OPT_IN_REQUIRED_FQ_NAME = fqName3;
        ClassId classId3 = ClassId.topLevel(fqName3);
        Intrinsics.checkNotNullExpressionValue(classId3, "topLevel(...)");
        SUBCLASS_OPT_IN_REQUIRED_CLASS_ID = classId3;
        FqName fqName4 = new FqName("kotlin.WasExperimental");
        WAS_EXPERIMENTAL_FQ_NAME = fqName4;
        ClassId classId4 = ClassId.topLevel(fqName4);
        Intrinsics.checkNotNullExpressionValue(classId4, "topLevel(...)");
        WAS_EXPERIMENTAL_CLASS_ID = classId4;
        Name nameIdentifier = Name.identifier("markerClass");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        OPT_IN_ANNOTATION_CLASS = nameIdentifier;
        Name nameIdentifier2 = Name.identifier("markerClass");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        WAS_EXPERIMENTAL_ANNOTATION_CLASS = nameIdentifier2;
    }

    public final FqName getREQUIRES_OPT_IN_FQ_NAME() {
        return REQUIRES_OPT_IN_FQ_NAME;
    }

    public final ClassId getREQUIRES_OPT_IN_CLASS_ID() {
        return REQUIRES_OPT_IN_CLASS_ID;
    }

    public final FqName getOPT_IN_FQ_NAME() {
        return OPT_IN_FQ_NAME;
    }

    public final ClassId getOPT_IN_CLASS_ID() {
        return OPT_IN_CLASS_ID;
    }

    public final FqName getSUBCLASS_OPT_IN_REQUIRED_FQ_NAME() {
        return SUBCLASS_OPT_IN_REQUIRED_FQ_NAME;
    }

    public final ClassId getSUBCLASS_OPT_IN_REQUIRED_CLASS_ID() {
        return SUBCLASS_OPT_IN_REQUIRED_CLASS_ID;
    }

    public final FqName getWAS_EXPERIMENTAL_FQ_NAME() {
        return WAS_EXPERIMENTAL_FQ_NAME;
    }

    public final ClassId getWAS_EXPERIMENTAL_CLASS_ID() {
        return WAS_EXPERIMENTAL_CLASS_ID;
    }

    public final Name getOPT_IN_ANNOTATION_CLASS() {
        return OPT_IN_ANNOTATION_CLASS;
    }

    public final Name getWAS_EXPERIMENTAL_ANNOTATION_CLASS() {
        return WAS_EXPERIMENTAL_ANNOTATION_CLASS;
    }

    public final String buildDefaultDiagnosticMessage(String prefix, String markerName) {
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        Intrinsics.checkNotNullParameter(markerName, "markerName");
        return prefix + " with '@" + markerName + "' or '@OptIn(" + markerName + "::class)'";
    }

    public final String buildMessagePrefix(String verb) {
        Intrinsics.checkNotNullParameter(verb, "verb");
        return "This declaration needs opt-in. Its usage " + verb + " be marked";
    }

    /* JADX WARN: Code duplicated, block: B:16:0x0064  */
    public final String buildOverrideMessage(String supertypeName, String markerMessage, String verb, String markerName) {
        String string;
        Intrinsics.checkNotNullParameter(supertypeName, "supertypeName");
        Intrinsics.checkNotNullParameter(verb, "verb");
        Intrinsics.checkNotNullParameter(markerName, "markerName");
        String str = "Base declaration of supertype '" + supertypeName + "' needs opt-in. ";
        if (markerMessage == null) {
            string = "";
        } else {
            if (StringsKt.isBlank(markerMessage)) {
                markerMessage = null;
            }
            if (markerMessage == null) {
                string = "";
            } else {
                string = (StringsKt.endsWith$default(markerMessage, ".", false, 2, (Object) null) ? new StringBuilder().append(markerMessage).append(StringUtil.SPACE) : new StringBuilder().append(markerMessage).append(". ")).toString();
                if (string == null) {
                    string = "";
                }
            }
        }
        return str + string + buildDefaultDiagnosticMessage("The declaration override " + verb + " be annotated", markerName);
    }
}
