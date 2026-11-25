package org.jetbrains.kotlin.contracts.model;

import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.contracts.model.structure.ESType;

/* JADX INFO: compiled from: Computation.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001R\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u0004\u0018\u00010\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/contracts/model/Computation;", "", "effects", "", "Lorg/jetbrains/kotlin/contracts/model/ESEffect;", "getEffects", "()Ljava/util/List;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/contracts/model/structure/ESType;", "getType", "()Lorg/jetbrains/kotlin/contracts/model/structure/ESType;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface Computation {
    List<ESEffect> getEffects();

    ESType getType();
}
