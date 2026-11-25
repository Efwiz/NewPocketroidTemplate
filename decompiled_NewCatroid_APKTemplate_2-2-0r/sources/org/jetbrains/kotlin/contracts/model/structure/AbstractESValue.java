package org.jetbrains.kotlin.contracts.model.structure;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.contracts.model.ESEffect;
import org.jetbrains.kotlin.contracts.model.ESValue;

/* JADX INFO: compiled from: Values.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\b&\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004R\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/contracts/model/structure/AbstractESValue;", "Lorg/jetbrains/kotlin/contracts/model/ESValue;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/contracts/model/structure/ESType;", "(Lorg/jetbrains/kotlin/contracts/model/structure/ESType;)V", "effects", "", "Lorg/jetbrains/kotlin/contracts/model/ESEffect;", "getEffects", "()Ljava/util/List;", "getType", "()Lorg/jetbrains/kotlin/contracts/model/structure/ESType;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractESValue implements ESValue {
    private final List<ESEffect> effects = CollectionsKt.emptyList();
    private final ESType type;

    public AbstractESValue(ESType eSType) {
        this.type = eSType;
    }

    @Override // org.jetbrains.kotlin.contracts.model.Computation
    public ESType getType() {
        return this.type;
    }

    @Override // org.jetbrains.kotlin.contracts.model.Computation
    public List<ESEffect> getEffects() {
        return this.effects;
    }
}
