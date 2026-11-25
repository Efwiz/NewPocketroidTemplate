package org.jetbrains.kotlin.types;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: AbstractTypePreparator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001:\u0001\u0006B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H&¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/types/AbstractTypePreparator;", "", "()V", "prepareType", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", ModuleXmlParser.TYPE, "Default", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractTypePreparator {
    public abstract KotlinTypeMarker prepareType(KotlinTypeMarker type);

    /* JADX INFO: compiled from: AbstractTypePreparator.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0016¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/types/AbstractTypePreparator$Default;", "Lorg/jetbrains/kotlin/types/AbstractTypePreparator;", "()V", "prepareType", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", ModuleXmlParser.TYPE, "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Default extends AbstractTypePreparator {
        public static final Default INSTANCE = new Default();

        @Override // org.jetbrains.kotlin.types.AbstractTypePreparator
        public KotlinTypeMarker prepareType(KotlinTypeMarker type) {
            Intrinsics.checkNotNullParameter(type, "type");
            return type;
        }

        private Default() {
        }
    }
}
