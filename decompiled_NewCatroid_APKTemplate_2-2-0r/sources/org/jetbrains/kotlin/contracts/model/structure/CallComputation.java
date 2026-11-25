package org.jetbrains.kotlin.contracts.model.structure;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.contracts.model.Computation;
import org.jetbrains.kotlin.contracts.model.ESEffect;

/* JADX INFO: compiled from: Computations.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u001d\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/contracts/model/structure/CallComputation;", "Lorg/jetbrains/kotlin/contracts/model/Computation;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/contracts/model/structure/ESType;", "effects", "", "Lorg/jetbrains/kotlin/contracts/model/ESEffect;", "(Lorg/jetbrains/kotlin/contracts/model/structure/ESType;Ljava/util/List;)V", "getEffects", "()Ljava/util/List;", "getType", "()Lorg/jetbrains/kotlin/contracts/model/structure/ESType;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CallComputation implements Computation {
    private final List<ESEffect> effects;
    private final ESType type;

    /* JADX WARN: Multi-variable type inference failed */
    public CallComputation(ESType eSType, List<? extends ESEffect> effects) {
        Intrinsics.checkNotNullParameter(effects, "effects");
        this.type = eSType;
        this.effects = effects;
    }

    @Override // org.jetbrains.kotlin.contracts.model.Computation
    public List<ESEffect> getEffects() {
        return this.effects;
    }

    @Override // org.jetbrains.kotlin.contracts.model.Computation
    public ESType getType() {
        return this.type;
    }
}
