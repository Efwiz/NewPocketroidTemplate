package org.jetbrains.kotlin.fir.resolve;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;

/* JADX INFO: compiled from: FirDoubleColonExpressionResolver.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u0007\bB\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0002\t\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/DoubleColonLHS;", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "(Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;)V", "getType", "()Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Expression", "Type", "Lorg/jetbrains/kotlin/fir/resolve/DoubleColonLHS$Expression;", "Lorg/jetbrains/kotlin/fir/resolve/DoubleColonLHS$Type;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class DoubleColonLHS {
    private final ConeKotlinType type;

    public /* synthetic */ DoubleColonLHS(ConeKotlinType coneKotlinType, DefaultConstructorMarker defaultConstructorMarker) {
        this(coneKotlinType);
    }

    private DoubleColonLHS(ConeKotlinType coneKotlinType) {
        this.type = coneKotlinType;
    }

    public final ConeKotlinType getType() {
        return this.type;
    }

    /* JADX INFO: compiled from: FirDoubleColonExpressionResolver.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/DoubleColonLHS$Expression;", "Lorg/jetbrains/kotlin/fir/resolve/DoubleColonLHS;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "isObjectQualifier", "", "(Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;Z)V", "()Z", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Expression extends DoubleColonLHS {
        private final boolean isObjectQualifier;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Expression(ConeKotlinType type, boolean z) {
            super(type, null);
            Intrinsics.checkNotNullParameter(type, "type");
            this.isObjectQualifier = z;
        }

        /* JADX INFO: renamed from: isObjectQualifier, reason: from getter */
        public final boolean getIsObjectQualifier() {
            return this.isObjectQualifier;
        }
    }

    /* JADX INFO: compiled from: FirDoubleColonExpressionResolver.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/DoubleColonLHS$Type;", "Lorg/jetbrains/kotlin/fir/resolve/DoubleColonLHS;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "(Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;)V", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Type extends DoubleColonLHS {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Type(ConeKotlinType type) {
            super(type, null);
            Intrinsics.checkNotNullParameter(type, "type");
        }
    }
}
