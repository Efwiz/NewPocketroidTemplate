package org.jetbrains.kotlin.cli.common;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.phaser.AbstractNamedCompilerPhase;
import org.jetbrains.kotlin.backend.common.phaser.CompilerPhase;
import org.jetbrains.kotlin.backend.common.phaser.PhaseConfig;
import org.jetbrains.kotlin.backend.common.phaser.PhaseConfigKt;
import org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;

/* JADX INFO: compiled from: createPhaseConfig.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000F\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001ak\u0010\u0000\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0002j\u0002`\u00030\u00012\"\u0010\u0004\u001a\u001e\u0012\u0004\u0012\u00020\u0006\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0002j\u0002`\u00030\u00052\u000e\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u000b0\nH\u0002¢\u0006\u0002\u0010\f\u001a*\u0010\r\u001a\u00020\u000e2\u0012\u0010\u000f\u001a\u000e\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014\u001ak\u0010\u0015\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0002j\u0002`\u00030\u00012\"\u0010\u0004\u001a\u001e\u0012\u0004\u0012\u00020\u0006\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0002j\u0002`\u00030\u00052\u000e\u0010\u0016\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u000b0\nH\u0002¢\u0006\u0002\u0010\f¨\u0006\u0017"}, d2 = {"computeEnabled", "", "Lorg/jetbrains/kotlin/backend/common/phaser/AbstractNamedCompilerPhase;", "Lorg/jetbrains/kotlin/backend/common/phaser/AnyNamedPhase;", "phases", "", "", "namesOfDisabled", "", "report", "Lkotlin/Function1;", "", "(Ljava/util/Map;[Ljava/lang/String;Lkotlin/jvm/functions/Function1;)Ljava/util/Set;", "createPhaseConfig", "Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfig;", "compoundPhase", "Lorg/jetbrains/kotlin/backend/common/phaser/CompilerPhase;", "arguments", "Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "phaseSetFromArguments", "names", "cli"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CreatePhaseConfigKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final void createPhaseConfig$report(MessageCollector messageCollector, String str) {
        MessageCollector.report$default(messageCollector, CompilerMessageSeverity.ERROR, str, null, 4, null);
    }

    public static final PhaseConfig createPhaseConfig(CompilerPhase<?, ?, ?> compoundPhase, CommonCompilerArguments arguments, MessageCollector messageCollector) {
        Intrinsics.checkNotNullParameter(compoundPhase, "compoundPhase");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        Map<String, AbstractNamedCompilerPhase<?, ?, ?>> phaseMap = PhaseConfigKt.toPhaseMap(compoundPhase);
        Set mutableSet = CollectionsKt.toMutableSet(computeEnabled(phaseMap, arguments.getDisablePhases(), new CreatePhaseConfigKt$createPhaseConfig$enabled$1(messageCollector)));
        Set<AbstractNamedCompilerPhase<?, ?, ?>> setPhaseSetFromArguments = phaseSetFromArguments(phaseMap, arguments.getVerbosePhases(), new CreatePhaseConfigKt$createPhaseConfig$verbose$1(messageCollector));
        Set<AbstractNamedCompilerPhase<?, ?, ?>> setPhaseSetFromArguments2 = phaseSetFromArguments(phaseMap, arguments.getPhasesToDumpBefore(), new CreatePhaseConfigKt$createPhaseConfig$beforeDumpSet$1(messageCollector));
        Set<AbstractNamedCompilerPhase<?, ?, ?>> setPhaseSetFromArguments3 = phaseSetFromArguments(phaseMap, arguments.getPhasesToDumpAfter(), new CreatePhaseConfigKt$createPhaseConfig$afterDumpSet$1(messageCollector));
        Set<AbstractNamedCompilerPhase<?, ?, ?>> setPhaseSetFromArguments4 = phaseSetFromArguments(phaseMap, arguments.getPhasesToDump(), new CreatePhaseConfigKt$createPhaseConfig$bothDumpSet$1(messageCollector));
        Set setPlus = SetsKt.plus((Set) setPhaseSetFromArguments2, (Iterable) setPhaseSetFromArguments4);
        Set setPlus2 = SetsKt.plus((Set) setPhaseSetFromArguments3, (Iterable) setPhaseSetFromArguments4);
        String dumpDirectory = arguments.getDumpDirectory();
        String dumpOnlyFqName = arguments.getDumpOnlyFqName();
        Set<AbstractNamedCompilerPhase<?, ?, ?>> setPhaseSetFromArguments5 = phaseSetFromArguments(phaseMap, arguments.getPhasesToValidateBefore(), new CreatePhaseConfigKt$createPhaseConfig$beforeValidateSet$1(messageCollector));
        Set<AbstractNamedCompilerPhase<?, ?, ?>> setPhaseSetFromArguments6 = phaseSetFromArguments(phaseMap, arguments.getPhasesToValidateAfter(), new CreatePhaseConfigKt$createPhaseConfig$afterValidateSet$1(messageCollector));
        Set<AbstractNamedCompilerPhase<?, ?, ?>> setPhaseSetFromArguments7 = phaseSetFromArguments(phaseMap, arguments.getPhasesToValidate(), new CreatePhaseConfigKt$createPhaseConfig$bothValidateSet$1(messageCollector));
        PhaseConfig phaseConfig = new PhaseConfig(compoundPhase, phaseMap, mutableSet, setPhaseSetFromArguments, setPlus, setPlus2, dumpDirectory, dumpOnlyFqName, SetsKt.plus((Set) setPhaseSetFromArguments5, (Iterable) setPhaseSetFromArguments7), SetsKt.plus((Set) setPhaseSetFromArguments6, (Iterable) setPhaseSetFromArguments7), arguments.getProfilePhases(), arguments.getCheckPhaseConditions(), arguments.getCheckStickyPhaseConditions());
        if (arguments.getListPhases()) {
            phaseConfig.list();
        }
        return phaseConfig;
    }

    private static final Set<AbstractNamedCompilerPhase<?, ?, ?>> computeEnabled(Map<String, AbstractNamedCompilerPhase<?, ?, ?>> map, String[] strArr, Function1<? super String, Unit> function1) {
        return SetsKt.minus(CollectionsKt.toSet(map.values()), (Iterable) phaseSetFromArguments(map, strArr, function1));
    }

    private static final Set<AbstractNamedCompilerPhase<?, ?, ?>> phaseSetFromArguments(Map<String, AbstractNamedCompilerPhase<?, ?, ?>> map, String[] strArr, Function1<? super String, Unit> function1) {
        if (strArr == null) {
            return SetsKt.emptySet();
        }
        if (ArraysKt.contains(strArr, "ALL")) {
            return CollectionsKt.toSet(map.values());
        }
        ArrayList arrayList = new ArrayList();
        for (String str : strArr) {
            AbstractNamedCompilerPhase<?, ?, ?> abstractNamedCompilerPhase = map.get(str);
            if (abstractNamedCompilerPhase == null) {
                function1.invoke("no phase named " + str);
                abstractNamedCompilerPhase = null;
            }
            if (abstractNamedCompilerPhase != null) {
                arrayList.add(abstractNamedCompilerPhase);
            }
        }
        return CollectionsKt.toSet(arrayList);
    }
}
