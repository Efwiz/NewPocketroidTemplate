package org.jetbrains.kotlin;

import com.huawei.hms.mlplugin.asr.MLAsrCaptureConstants;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import org.jetbrains.kotlin.name.JvmNames;

/* JADX INFO: compiled from: JvmFieldApplicabilityProblem.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000f\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/JvmFieldApplicabilityProblem;", "", MLAsrCaptureConstants.ASR_ERROR_MESSAGE, "", "(Ljava/lang/String;ILjava/lang/String;)V", "getErrorMessage", "()Ljava/lang/String;", "NOT_FINAL", "PRIVATE", "CUSTOM_ACCESSOR", "OVERRIDES", "LATEINIT", "CONST", "INSIDE_COMPANION_OF_INTERFACE", "NOT_PUBLIC_VAL_WITH_JVMFIELD", "TOP_LEVEL_PROPERTY_OF_MULTIFILE_FACADE", "DELEGATE", "RETURN_TYPE_IS_VALUE_CLASS", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum JvmFieldApplicabilityProblem {
    NOT_FINAL("JvmField can only be applied to final property"),
    PRIVATE("JvmField has no effect on a private property"),
    CUSTOM_ACCESSOR("JvmField cannot be applied to a property with a custom accessor"),
    OVERRIDES("JvmField cannot be applied to a property that overrides some other property"),
    LATEINIT("JvmField cannot be applied to lateinit property"),
    CONST("JvmField cannot be applied to const property"),
    INSIDE_COMPANION_OF_INTERFACE("JvmField cannot be applied to a property defined in companion object of interface"),
    NOT_PUBLIC_VAL_WITH_JVMFIELD("JvmField could be applied only if all interface companion properties are 'public final val' with '@JvmField' annotation"),
    TOP_LEVEL_PROPERTY_OF_MULTIFILE_FACADE("JvmField cannot be applied to top level property of a file annotated with " + JvmNames.INSTANCE.getJVM_MULTIFILE_CLASS_SHORT()),
    DELEGATE("JvmField cannot be applied to delegated property"),
    RETURN_TYPE_IS_VALUE_CLASS("JvmField cannot be applied to a property of a value class type");

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    private final String errorMessage;

    public static EnumEntries<JvmFieldApplicabilityProblem> getEntries() {
        return $ENTRIES;
    }

    JvmFieldApplicabilityProblem(String str) {
        this.errorMessage = str;
    }

    public final String getErrorMessage() {
        return this.errorMessage;
    }
}
