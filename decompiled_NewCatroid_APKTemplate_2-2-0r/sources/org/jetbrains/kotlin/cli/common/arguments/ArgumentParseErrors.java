package org.jetbrains.kotlin.cli.common.arguments;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: parseCommandLineArguments.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\b \n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0099\u0001\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0014\b\u0002\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\b\u0012\u0014\b\u0002\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\b\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0004\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0004\u0012\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u000eJ\u000f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u000f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u000f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u0015\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\bHÆ\u0003J\u0015\u0010\"\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\bHÆ\u0003J\u000b\u0010#\u001a\u0004\u0018\u00010\u0004HÆ\u0003J\u000b\u0010$\u001a\u0004\u0018\u00010\u0004HÆ\u0003J\u000f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u000f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u009d\u0001\u0010'\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0014\b\u0002\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\b2\u0014\b\u0002\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\b2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00042\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0001J\u0013\u0010(\u001a\u00020)2\b\u0010*\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010+\u001a\u00020,HÖ\u0001J\t\u0010-\u001a\u00020\u0004HÖ\u0001R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001c\u0010\n\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0012\"\u0004\b\u0016\u0010\u0014R\u001d\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\b¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u001d\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\b¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0018R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0010R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0010R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0010R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0010¨\u0006."}, d2 = {"Lorg/jetbrains/kotlin/cli/common/arguments/ArgumentParseErrors;", "", "unknownArgs", "", "", "unknownExtraFlags", "extraArgumentsPassedInObsoleteForm", "duplicateArguments", "", "deprecatedArguments", "argumentWithoutValue", "booleanArgumentWithValue", "argfileErrors", "internalArgumentsParsingProblems", "(Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/Map;Ljava/util/Map;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/util/List;)V", "getArgfileErrors", "()Ljava/util/List;", "getArgumentWithoutValue", "()Ljava/lang/String;", "setArgumentWithoutValue", "(Ljava/lang/String;)V", "getBooleanArgumentWithValue", "setBooleanArgumentWithValue", "getDeprecatedArguments", "()Ljava/util/Map;", "getDuplicateArguments", "getExtraArgumentsPassedInObsoleteForm", "getInternalArgumentsParsingProblems", "getUnknownArgs", "getUnknownExtraFlags", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ArgumentParseErrors {
    private final List<String> argfileErrors;
    private String argumentWithoutValue;
    private String booleanArgumentWithValue;
    private final Map<String, String> deprecatedArguments;
    private final Map<String, String> duplicateArguments;
    private final List<String> extraArgumentsPassedInObsoleteForm;
    private final List<String> internalArgumentsParsingProblems;
    private final List<String> unknownArgs;
    private final List<String> unknownExtraFlags;

    public ArgumentParseErrors() {
        this(null, null, null, null, null, null, null, null, null, 511, null);
    }

    public final List<String> component1() {
        return this.unknownArgs;
    }

    public final List<String> component2() {
        return this.unknownExtraFlags;
    }

    public final List<String> component3() {
        return this.extraArgumentsPassedInObsoleteForm;
    }

    public final Map<String, String> component4() {
        return this.duplicateArguments;
    }

    public final Map<String, String> component5() {
        return this.deprecatedArguments;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final String getArgumentWithoutValue() {
        return this.argumentWithoutValue;
    }

    /* JADX INFO: renamed from: component7, reason: from getter */
    public final String getBooleanArgumentWithValue() {
        return this.booleanArgumentWithValue;
    }

    public final List<String> component8() {
        return this.argfileErrors;
    }

    public final List<String> component9() {
        return this.internalArgumentsParsingProblems;
    }

    public final ArgumentParseErrors copy(List<String> unknownArgs, List<String> unknownExtraFlags, List<String> extraArgumentsPassedInObsoleteForm, Map<String, String> duplicateArguments, Map<String, String> deprecatedArguments, String argumentWithoutValue, String booleanArgumentWithValue, List<String> argfileErrors, List<String> internalArgumentsParsingProblems) {
        Intrinsics.checkNotNullParameter(unknownArgs, "unknownArgs");
        Intrinsics.checkNotNullParameter(unknownExtraFlags, "unknownExtraFlags");
        Intrinsics.checkNotNullParameter(extraArgumentsPassedInObsoleteForm, "extraArgumentsPassedInObsoleteForm");
        Intrinsics.checkNotNullParameter(duplicateArguments, "duplicateArguments");
        Intrinsics.checkNotNullParameter(deprecatedArguments, "deprecatedArguments");
        Intrinsics.checkNotNullParameter(argfileErrors, "argfileErrors");
        Intrinsics.checkNotNullParameter(internalArgumentsParsingProblems, "internalArgumentsParsingProblems");
        return new ArgumentParseErrors(unknownArgs, unknownExtraFlags, extraArgumentsPassedInObsoleteForm, duplicateArguments, deprecatedArguments, argumentWithoutValue, booleanArgumentWithValue, argfileErrors, internalArgumentsParsingProblems);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ArgumentParseErrors)) {
            return false;
        }
        ArgumentParseErrors argumentParseErrors = (ArgumentParseErrors) other;
        return Intrinsics.areEqual(this.unknownArgs, argumentParseErrors.unknownArgs) && Intrinsics.areEqual(this.unknownExtraFlags, argumentParseErrors.unknownExtraFlags) && Intrinsics.areEqual(this.extraArgumentsPassedInObsoleteForm, argumentParseErrors.extraArgumentsPassedInObsoleteForm) && Intrinsics.areEqual(this.duplicateArguments, argumentParseErrors.duplicateArguments) && Intrinsics.areEqual(this.deprecatedArguments, argumentParseErrors.deprecatedArguments) && Intrinsics.areEqual(this.argumentWithoutValue, argumentParseErrors.argumentWithoutValue) && Intrinsics.areEqual(this.booleanArgumentWithValue, argumentParseErrors.booleanArgumentWithValue) && Intrinsics.areEqual(this.argfileErrors, argumentParseErrors.argfileErrors) && Intrinsics.areEqual(this.internalArgumentsParsingProblems, argumentParseErrors.internalArgumentsParsingProblems);
    }

    public int hashCode() {
        int iHashCode = ((((((((this.unknownArgs.hashCode() * 31) + this.unknownExtraFlags.hashCode()) * 31) + this.extraArgumentsPassedInObsoleteForm.hashCode()) * 31) + this.duplicateArguments.hashCode()) * 31) + this.deprecatedArguments.hashCode()) * 31;
        String str = this.argumentWithoutValue;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.booleanArgumentWithValue;
        return ((((iHashCode2 + (str2 != null ? str2.hashCode() : 0)) * 31) + this.argfileErrors.hashCode()) * 31) + this.internalArgumentsParsingProblems.hashCode();
    }

    public String toString() {
        return "ArgumentParseErrors(unknownArgs=" + this.unknownArgs + ", unknownExtraFlags=" + this.unknownExtraFlags + ", extraArgumentsPassedInObsoleteForm=" + this.extraArgumentsPassedInObsoleteForm + ", duplicateArguments=" + this.duplicateArguments + ", deprecatedArguments=" + this.deprecatedArguments + ", argumentWithoutValue=" + this.argumentWithoutValue + ", booleanArgumentWithValue=" + this.booleanArgumentWithValue + ", argfileErrors=" + this.argfileErrors + ", internalArgumentsParsingProblems=" + this.internalArgumentsParsingProblems + ')';
    }

    public ArgumentParseErrors(List<String> unknownArgs, List<String> unknownExtraFlags, List<String> extraArgumentsPassedInObsoleteForm, Map<String, String> duplicateArguments, Map<String, String> deprecatedArguments, String str, String str2, List<String> argfileErrors, List<String> internalArgumentsParsingProblems) {
        Intrinsics.checkNotNullParameter(unknownArgs, "unknownArgs");
        Intrinsics.checkNotNullParameter(unknownExtraFlags, "unknownExtraFlags");
        Intrinsics.checkNotNullParameter(extraArgumentsPassedInObsoleteForm, "extraArgumentsPassedInObsoleteForm");
        Intrinsics.checkNotNullParameter(duplicateArguments, "duplicateArguments");
        Intrinsics.checkNotNullParameter(deprecatedArguments, "deprecatedArguments");
        Intrinsics.checkNotNullParameter(argfileErrors, "argfileErrors");
        Intrinsics.checkNotNullParameter(internalArgumentsParsingProblems, "internalArgumentsParsingProblems");
        this.unknownArgs = unknownArgs;
        this.unknownExtraFlags = unknownExtraFlags;
        this.extraArgumentsPassedInObsoleteForm = extraArgumentsPassedInObsoleteForm;
        this.duplicateArguments = duplicateArguments;
        this.deprecatedArguments = deprecatedArguments;
        this.argumentWithoutValue = str;
        this.booleanArgumentWithValue = str2;
        this.argfileErrors = argfileErrors;
        this.internalArgumentsParsingProblems = internalArgumentsParsingProblems;
    }

    public /* synthetic */ ArgumentParseErrors(List list, List list2, List list3, Map map, Map map2, String str, String str2, List list4, List list5, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new SmartList() : list, (i & 2) != 0 ? new SmartList() : list2, (i & 4) != 0 ? new SmartList() : list3, (i & 8) != 0 ? new LinkedHashMap() : map, (i & 16) != 0 ? new LinkedHashMap() : map2, (i & 32) != 0 ? null : str, (i & 64) == 0 ? str2 : null, (i & 128) != 0 ? new SmartList() : list4, (i & 256) != 0 ? new SmartList() : list5);
    }

    public final List<String> getUnknownArgs() {
        return this.unknownArgs;
    }

    public final List<String> getUnknownExtraFlags() {
        return this.unknownExtraFlags;
    }

    public final List<String> getExtraArgumentsPassedInObsoleteForm() {
        return this.extraArgumentsPassedInObsoleteForm;
    }

    public final Map<String, String> getDuplicateArguments() {
        return this.duplicateArguments;
    }

    public final Map<String, String> getDeprecatedArguments() {
        return this.deprecatedArguments;
    }

    public final String getArgumentWithoutValue() {
        return this.argumentWithoutValue;
    }

    public final void setArgumentWithoutValue(String str) {
        this.argumentWithoutValue = str;
    }

    public final String getBooleanArgumentWithValue() {
        return this.booleanArgumentWithValue;
    }

    public final void setBooleanArgumentWithValue(String str) {
        this.booleanArgumentWithValue = str;
    }

    public final List<String> getArgfileErrors() {
        return this.argfileErrors;
    }

    public final List<String> getInternalArgumentsParsingProblems() {
        return this.internalArgumentsParsingProblems;
    }
}
