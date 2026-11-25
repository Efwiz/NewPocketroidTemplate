package org.jetbrains.kotlin.backend.common.phaser;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PhaseConfig.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a2\u0010\u0000\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0003j\u0002`\u00040\u0001*\u000e\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0005¨\u0006\u0006"}, d2 = {"toPhaseMap", "", "", "Lorg/jetbrains/kotlin/backend/common/phaser/AbstractNamedCompilerPhase;", "Lorg/jetbrains/kotlin/backend/common/phaser/AnyNamedPhase;", "Lorg/jetbrains/kotlin/backend/common/phaser/CompilerPhase;", "ir.backend.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PhaseConfigKt {
    public static final Map<String, AbstractNamedCompilerPhase<?, ?, ?>> toPhaseMap(CompilerPhase<?, ?, ?> compilerPhase) {
        Intrinsics.checkNotNullParameter(compilerPhase, "<this>");
        List namedSubphases$default = CompilerPhase.getNamedSubphases$default(compilerPhase, 0, 1, null);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator it = namedSubphases$default.iterator();
        while (it.hasNext()) {
            AbstractNamedCompilerPhase abstractNamedCompilerPhase = (AbstractNamedCompilerPhase) ((Pair) it.next()).component2();
            if (linkedHashMap.containsKey(abstractNamedCompilerPhase.getName())) {
                throw new IllegalStateException(("Duplicate phase name '" + abstractNamedCompilerPhase.getName() + '\'').toString());
            }
            linkedHashMap.put(abstractNamedCompilerPhase.getName(), abstractNamedCompilerPhase);
        }
        return linkedHashMap;
    }
}
