package org.jetbrains.kotlin.cli.common;

import java.util.ArrayList;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.phaser.FlexiblePhaseConfig;
import org.jetbrains.kotlin.backend.common.phaser.PhaseSet;
import org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments;
import org.jetbrains.kotlin.cli.common.arguments.DevModeOverwritingStrategies;
import org.jetbrains.kotlin.util.capitalizeDecapitalize.CapitalizeDecapitalizeKt;

/* JADX INFO: compiled from: createFlexiblePhaseConfig.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u001d\u0010\u0004\u001a\u00020\u00052\u000e\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007H\u0002¢\u0006\u0002\u0010\t¨\u0006\n"}, d2 = {"createFlexiblePhaseConfig", "Lorg/jetbrains/kotlin/backend/common/phaser/FlexiblePhaseConfig;", "arguments", "Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;", "createPhaseSetFromArguments", "Lorg/jetbrains/kotlin/backend/common/phaser/PhaseSet;", "names", "", "", "([Ljava/lang/String;)Lorg/jetbrains/kotlin/backend/common/phaser/PhaseSet;", "cli"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CreateFlexiblePhaseConfigKt {
    private static final Set<String> createFlexiblePhaseConfig$asNonNullSet(String[] strArr) {
        Set<String> set;
        return (strArr == null || (set = ArraysKt.toSet(strArr)) == null) ? SetsKt.emptySet() : set;
    }

    public static final FlexiblePhaseConfig createFlexiblePhaseConfig(CommonCompilerArguments arguments) {
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        PhaseSet phaseSetCreatePhaseSetFromArguments = createPhaseSetFromArguments(arguments.getPhasesToDump());
        PhaseSet phaseSetCreatePhaseSetFromArguments2 = createPhaseSetFromArguments(arguments.getPhasesToValidate());
        return new FlexiblePhaseConfig(createFlexiblePhaseConfig$asNonNullSet(arguments.getDisablePhases()), createFlexiblePhaseConfig$asNonNullSet(arguments.getVerbosePhases()), createPhaseSetFromArguments(arguments.getPhasesToDumpBefore()).plus(phaseSetCreatePhaseSetFromArguments), createPhaseSetFromArguments(arguments.getPhasesToDumpAfter()).plus(phaseSetCreatePhaseSetFromArguments), createPhaseSetFromArguments(arguments.getPhasesToValidateBefore()).plus(phaseSetCreatePhaseSetFromArguments2), createPhaseSetFromArguments(arguments.getPhasesToValidateAfter()).plus(phaseSetCreatePhaseSetFromArguments2), arguments.getDumpDirectory(), arguments.getDumpOnlyFqName(), arguments.getProfilePhases(), arguments.getCheckPhaseConditions(), arguments.getCheckStickyPhaseConditions());
    }

    private static final PhaseSet createPhaseSetFromArguments(String[] strArr) {
        if (strArr == null) {
            return new PhaseSet.Enum(SetsKt.emptySet());
        }
        ArrayList arrayList = new ArrayList(strArr.length);
        for (String str : strArr) {
            arrayList.add(CapitalizeDecapitalizeKt.toLowerCaseAsciiOnly(str));
        }
        if (arrayList.contains(DevModeOverwritingStrategies.ALL)) {
            return PhaseSet.ALL.INSTANCE;
        }
        ArrayList arrayList2 = new ArrayList(strArr.length);
        for (String str2 : strArr) {
            arrayList2.add(CapitalizeDecapitalizeKt.toLowerCaseAsciiOnly(str2));
        }
        return new PhaseSet.Enum(CollectionsKt.toSet(arrayList2));
    }
}
