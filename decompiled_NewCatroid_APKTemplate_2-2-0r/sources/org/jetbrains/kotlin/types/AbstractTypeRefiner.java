package org.jetbrains.kotlin.types;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: AbstractTypeRefiner.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001:\u0001\u0006B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H'¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/types/AbstractTypeRefiner;", "", "()V", "refineType", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", ModuleXmlParser.TYPE, "Default", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractTypeRefiner {
    public abstract KotlinTypeMarker refineType(KotlinTypeMarker type);

    /* JADX INFO: compiled from: AbstractTypeRefiner.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0017¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/types/AbstractTypeRefiner$Default;", "Lorg/jetbrains/kotlin/types/AbstractTypeRefiner;", "()V", "refineType", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", ModuleXmlParser.TYPE, "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Default extends AbstractTypeRefiner {
        public static final Default INSTANCE = new Default();

        @Override // org.jetbrains.kotlin.types.AbstractTypeRefiner
        public KotlinTypeMarker refineType(KotlinTypeMarker type) {
            Intrinsics.checkNotNullParameter(type, "type");
            return type;
        }

        private Default() {
        }
    }
}
