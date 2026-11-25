package org.jetbrains.kotlin.types.expressions;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.resolve.PossiblyBareType;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: DoubleColonExpressionResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u0007\bB\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0002\t\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/types/expressions/DoubleColonLHS;", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "(Lorg/jetbrains/kotlin/types/KotlinType;)V", "getType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "Expression", "Type", "Lorg/jetbrains/kotlin/types/expressions/DoubleColonLHS$Expression;", "Lorg/jetbrains/kotlin/types/expressions/DoubleColonLHS$Type;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class DoubleColonLHS {
    private final KotlinType type;

    public /* synthetic */ DoubleColonLHS(KotlinType kotlinType, DefaultConstructorMarker defaultConstructorMarker) {
        this(kotlinType);
    }

    private DoubleColonLHS(KotlinType kotlinType) {
        this.type = kotlinType;
    }

    public final KotlinType getType() {
        return this.type;
    }

    /* JADX INFO: compiled from: DoubleColonExpressionResolver.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/types/expressions/DoubleColonLHS$Expression;", "Lorg/jetbrains/kotlin/types/expressions/DoubleColonLHS;", "typeInfo", "Lorg/jetbrains/kotlin/types/expressions/KotlinTypeInfo;", "isObjectQualifier", "", "(Lorg/jetbrains/kotlin/types/expressions/KotlinTypeInfo;Z)V", "dataFlowInfo", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "getDataFlowInfo", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "()Z", "getTypeInfo", "()Lorg/jetbrains/kotlin/types/expressions/KotlinTypeInfo;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Expression extends DoubleColonLHS {
        private final DataFlowInfo dataFlowInfo;
        private final boolean isObjectQualifier;
        private final KotlinTypeInfo typeInfo;

        /* JADX WARN: Illegal instructions before constructor call */
        public Expression(KotlinTypeInfo typeInfo, boolean z) {
            Intrinsics.checkNotNullParameter(typeInfo, "typeInfo");
            KotlinType type = typeInfo.getType();
            Intrinsics.checkNotNull(type);
            super(type, null);
            this.typeInfo = typeInfo;
            this.isObjectQualifier = z;
            this.dataFlowInfo = typeInfo.getDataFlowInfo();
        }

        public final KotlinTypeInfo getTypeInfo() {
            return this.typeInfo;
        }

        /* JADX INFO: renamed from: isObjectQualifier, reason: from getter */
        public final boolean getIsObjectQualifier() {
            return this.isObjectQualifier;
        }

        public final DataFlowInfo getDataFlowInfo() {
            return this.dataFlowInfo;
        }
    }

    /* JADX INFO: compiled from: DoubleColonExpressionResolver.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/types/expressions/DoubleColonLHS$Type;", "Lorg/jetbrains/kotlin/types/expressions/DoubleColonLHS;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "possiblyBareType", "Lorg/jetbrains/kotlin/resolve/PossiblyBareType;", "(Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/resolve/PossiblyBareType;)V", "getPossiblyBareType", "()Lorg/jetbrains/kotlin/resolve/PossiblyBareType;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Type extends DoubleColonLHS {
        private final PossiblyBareType possiblyBareType;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Type(KotlinType type, PossiblyBareType possiblyBareType) {
            super(type, null);
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(possiblyBareType, "possiblyBareType");
            this.possiblyBareType = possiblyBareType;
        }

        public final PossiblyBareType getPossiblyBareType() {
            return this.possiblyBareType;
        }
    }
}
