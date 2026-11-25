package org.jetbrains.kotlin.types;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.StringTemplate;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.types.model.CaptureStatus;
import org.jetbrains.kotlin.types.model.CapturedTypeMarker;
import org.jetbrains.kotlin.types.model.MarkerExtensionsKt;
import org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext;
import org.jetbrains.kotlin.types.model.TypeVariableTypeConstructorMarker;

/* JADX INFO: compiled from: TypeApproximatorConfiguration.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\b\u0016\u0018\u00002\u00020\u0001:\f)*+,-./01234B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u001f\u001a\u00020\u00042\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#H\u0016J\u001d\u0010$\u001a\u00020\u00042\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\u0004H\u0010¢\u0006\u0002\b(R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006R\u0014\u0010\t\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u0006R\u0014\u0010\u000b\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u0006R\u0014\u0010\r\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u0006R\u0014\u0010\u000f\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0006R\u0014\u0010\u0011\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0006R\u0014\u0010\u0013\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0006R\u0014\u0010\u0015\u001a\u00020\u0016X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0019\u001a\u00020\u0004X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0006R\u0014\u0010\u001b\u001a\u00020\u0004X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0006R\u0014\u0010\u001d\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u0006¨\u00065"}, d2 = {"Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration;", "", "()V", StringTemplate.ANONYMOUS_ST_NAME, "", "getAnonymous", "()Z", "convertToNonRawVersionAfterApproximationInK2", "getConvertToNonRawVersionAfterApproximationInK2", "definitelyNotNullType", "getDefinitelyNotNullType", "dynamic", "getDynamic", "errorType", "getErrorType", "flexible", "getFlexible", "integerConstantOperatorType", "getIntegerConstantOperatorType", "integerLiteralConstantType", "getIntegerLiteralConstantType", "intersection", "Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration$IntersectionStrategy;", "getIntersection", "()Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration$IntersectionStrategy;", "intersectionTypesInContravariantPositions", "getIntersectionTypesInContravariantPositions", "localTypes", "getLocalTypes", "rawType", "getRawType", "capturedType", "ctx", "Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/model/CapturedTypeMarker;", "shouldKeepTypeVariableBasedType", "marker", "Lorg/jetbrains/kotlin/types/model/TypeVariableTypeConstructorMarker;", "isK2", "shouldKeepTypeVariableBasedType$resolution_common", "AbstractCapturedTypesApproximation", "AllFlexibleSameValue", "FinalApproximationAfterResolutionAndInference", "IncorporationConfiguration", "IntegerLiteralsTypesApproximation", "InternalTypesApproximation", "IntersectionStrategy", "LocalDeclaration", "PublicDeclaration", "SubtypeCapturedTypesApproximation", "TypeArgumentApproximation", "UpperBoundAwareIntersectionTypeApproximator", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class TypeApproximatorConfiguration {
    private final boolean anonymous;
    private final IntersectionStrategy intersection = IntersectionStrategy.TO_COMMON_SUPERTYPE;
    private final boolean intersectionTypesInContravariantPositions;
    private final boolean localTypes;

    /* JADX INFO: compiled from: TypeApproximatorConfiguration.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration$IntersectionStrategy;", "", "(Ljava/lang/String;I)V", "ALLOWED", "TO_FIRST", "TO_COMMON_SUPERTYPE", "TO_UPPER_BOUND_IF_SUPERTYPE", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum IntersectionStrategy {
        ALLOWED,
        TO_FIRST,
        TO_COMMON_SUPERTYPE,
        TO_UPPER_BOUND_IF_SUPERTYPE;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<IntersectionStrategy> getEntries() {
            return $ENTRIES;
        }
    }

    public boolean capturedType(TypeSystemInferenceExtensionContext ctx, CapturedTypeMarker type) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(type, "type");
        return true;
    }

    public boolean getConvertToNonRawVersionAfterApproximationInK2() {
        return false;
    }

    public boolean getDefinitelyNotNullType() {
        return true;
    }

    public boolean getDynamic() {
        return false;
    }

    public boolean getErrorType() {
        return false;
    }

    public boolean getFlexible() {
        return false;
    }

    public boolean getIntegerConstantOperatorType() {
        return false;
    }

    public boolean getIntegerLiteralConstantType() {
        return false;
    }

    public boolean getRawType() {
        return false;
    }

    public boolean shouldKeepTypeVariableBasedType$resolution_common(TypeVariableTypeConstructorMarker marker, boolean isK2) {
        Intrinsics.checkNotNullParameter(marker, "marker");
        return false;
    }

    public IntersectionStrategy getIntersection() {
        return this.intersection;
    }

    public boolean getIntersectionTypesInContravariantPositions() {
        return this.intersectionTypesInContravariantPositions;
    }

    public boolean getLocalTypes() {
        return this.localTypes;
    }

    public boolean getAnonymous() {
        return this.anonymous;
    }

    /* JADX INFO: compiled from: TypeApproximatorConfiguration.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\t\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006R\u0014\u0010\t\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u0006R\u0014\u0010\u000b\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u0006¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration$AllFlexibleSameValue;", "Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration;", "()V", "allFlexible", "", "getAllFlexible", "()Z", "dynamic", "getDynamic", "flexible", "getFlexible", "rawType", "getRawType", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class AllFlexibleSameValue extends TypeApproximatorConfiguration {
        public abstract boolean getAllFlexible();

        @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration
        public boolean getFlexible() {
            return getAllFlexible();
        }

        @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration
        public boolean getDynamic() {
            return getAllFlexible();
        }

        @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration
        public boolean getRawType() {
            return getAllFlexible();
        }
    }

    /* JADX INFO: compiled from: TypeApproximatorConfiguration.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001d\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0004H\u0010¢\u0006\u0002\b\u0015R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006R\u0014\u0010\t\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u0006R\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0006¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration$LocalDeclaration;", "Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration$AllFlexibleSameValue;", "()V", "allFlexible", "", "getAllFlexible", "()Z", "errorType", "getErrorType", "integerLiteralConstantType", "getIntegerLiteralConstantType", "intersection", "Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration$IntersectionStrategy;", "getIntersection", "()Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration$IntersectionStrategy;", "intersectionTypesInContravariantPositions", "getIntersectionTypesInContravariantPositions", "shouldKeepTypeVariableBasedType", "marker", "Lorg/jetbrains/kotlin/types/model/TypeVariableTypeConstructorMarker;", "isK2", "shouldKeepTypeVariableBasedType$resolution_common", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class LocalDeclaration extends AllFlexibleSameValue {
        public static final LocalDeclaration INSTANCE = new LocalDeclaration();

        @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration.AllFlexibleSameValue
        public boolean getAllFlexible() {
            return true;
        }

        @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration
        public boolean getErrorType() {
            return true;
        }

        @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration
        public boolean getIntegerLiteralConstantType() {
            return true;
        }

        @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration
        public boolean getIntersectionTypesInContravariantPositions() {
            return true;
        }

        @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration
        public boolean shouldKeepTypeVariableBasedType$resolution_common(TypeVariableTypeConstructorMarker marker, boolean isK2) {
            Intrinsics.checkNotNullParameter(marker, "marker");
            return isK2;
        }

        private LocalDeclaration() {
        }

        @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration
        public IntersectionStrategy getIntersection() {
            return IntersectionStrategy.ALLOWED;
        }
    }

    /* JADX INFO: compiled from: TypeApproximatorConfiguration.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0016\u0018\u00002\u00020\u0001:\u0002\u0018\u0019B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\u001d\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0003H\u0010¢\u0006\u0002\b\u0017R\u0014\u0010\u0006\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0014\u0010\n\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\bR\u0014\u0010\f\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\bR\u0014\u0010\u000e\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\bR\u0014\u0010\u0010\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\b¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration$PublicDeclaration;", "Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration$AllFlexibleSameValue;", "localTypes", "", StringTemplate.ANONYMOUS_ST_NAME, "(ZZ)V", "allFlexible", "getAllFlexible", "()Z", "getAnonymous", "definitelyNotNullType", "getDefinitelyNotNullType", "errorType", "getErrorType", "integerLiteralConstantType", "getIntegerLiteralConstantType", "intersectionTypesInContravariantPositions", "getIntersectionTypesInContravariantPositions", "getLocalTypes", "shouldKeepTypeVariableBasedType", "marker", "Lorg/jetbrains/kotlin/types/model/TypeVariableTypeConstructorMarker;", "isK2", "shouldKeepTypeVariableBasedType$resolution_common", "ApproximateAnonymousTypes", "SaveAnonymousTypes", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static class PublicDeclaration extends AllFlexibleSameValue {
        private final boolean anonymous;
        private final boolean localTypes;

        @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration.AllFlexibleSameValue
        public boolean getAllFlexible() {
            return true;
        }

        @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration
        public boolean getDefinitelyNotNullType() {
            return false;
        }

        @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration
        public boolean getErrorType() {
            return true;
        }

        @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration
        public boolean getIntegerLiteralConstantType() {
            return true;
        }

        @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration
        public boolean getIntersectionTypesInContravariantPositions() {
            return true;
        }

        @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration
        public boolean shouldKeepTypeVariableBasedType$resolution_common(TypeVariableTypeConstructorMarker marker, boolean isK2) {
            Intrinsics.checkNotNullParameter(marker, "marker");
            return isK2;
        }

        public PublicDeclaration(boolean z, boolean z2) {
            this.localTypes = z;
            this.anonymous = z2;
        }

        @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration
        public boolean getAnonymous() {
            return this.anonymous;
        }

        @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration
        public boolean getLocalTypes() {
            return this.localTypes;
        }

        /* JADX INFO: compiled from: TypeApproximatorConfiguration.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration$PublicDeclaration$SaveAnonymousTypes;", "Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration$PublicDeclaration;", "()V", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class SaveAnonymousTypes extends PublicDeclaration {
            public static final SaveAnonymousTypes INSTANCE = new SaveAnonymousTypes();

            private SaveAnonymousTypes() {
                super(false, false);
            }
        }

        /* JADX INFO: compiled from: TypeApproximatorConfiguration.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration$PublicDeclaration$ApproximateAnonymousTypes;", "Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration$PublicDeclaration;", "()V", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class ApproximateAnonymousTypes extends PublicDeclaration {
            public static final ApproximateAnonymousTypes INSTANCE = new ApproximateAnonymousTypes();

            private ApproximateAnonymousTypes() {
                super(false, true);
            }
        }
    }

    /* JADX INFO: compiled from: TypeApproximatorConfiguration.kt */
    @Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u0011\b\u0004\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u001d\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0006H\u0010¢\u0006\u0002\b\u001aR\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\bR\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010\u0082\u0001\u0005\u001b\u001c\u001d\u001e\u001f¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration$AbstractCapturedTypesApproximation;", "Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration$AllFlexibleSameValue;", "approximatedCapturedStatus", "Lorg/jetbrains/kotlin/types/model/CaptureStatus;", "(Lorg/jetbrains/kotlin/types/model/CaptureStatus;)V", "allFlexible", "", "getAllFlexible", "()Z", "getApproximatedCapturedStatus", "()Lorg/jetbrains/kotlin/types/model/CaptureStatus;", "errorType", "getErrorType", "intersection", "Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration$IntersectionStrategy;", "getIntersection", "()Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration$IntersectionStrategy;", "capturedType", "ctx", "Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/model/CapturedTypeMarker;", "shouldKeepTypeVariableBasedType", "marker", "Lorg/jetbrains/kotlin/types/model/TypeVariableTypeConstructorMarker;", "isK2", "shouldKeepTypeVariableBasedType$resolution_common", "Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration$FinalApproximationAfterResolutionAndInference;", "Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration$IncorporationConfiguration;", "Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration$InternalTypesApproximation;", "Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration$SubtypeCapturedTypesApproximation;", "Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration$TypeArgumentApproximation;", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class AbstractCapturedTypesApproximation extends AllFlexibleSameValue {
        private final CaptureStatus approximatedCapturedStatus;

        public /* synthetic */ AbstractCapturedTypesApproximation(CaptureStatus captureStatus, DefaultConstructorMarker defaultConstructorMarker) {
            this(captureStatus);
        }

        @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration.AllFlexibleSameValue
        public boolean getAllFlexible() {
            return true;
        }

        @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration
        public boolean getErrorType() {
            return true;
        }

        @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration
        public boolean shouldKeepTypeVariableBasedType$resolution_common(TypeVariableTypeConstructorMarker marker, boolean isK2) {
            Intrinsics.checkNotNullParameter(marker, "marker");
            return true;
        }

        public final CaptureStatus getApproximatedCapturedStatus() {
            return this.approximatedCapturedStatus;
        }

        private AbstractCapturedTypesApproximation(CaptureStatus captureStatus) {
            this.approximatedCapturedStatus = captureStatus;
        }

        @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration
        public boolean capturedType(TypeSystemInferenceExtensionContext ctx, CapturedTypeMarker type) {
            Intrinsics.checkNotNullParameter(ctx, "ctx");
            Intrinsics.checkNotNullParameter(type, "type");
            return this.approximatedCapturedStatus != null && MarkerExtensionsKt.captureStatus(type, ctx) == this.approximatedCapturedStatus;
        }

        @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration
        public IntersectionStrategy getIntersection() {
            return IntersectionStrategy.ALLOWED;
        }
    }

    /* JADX INFO: compiled from: TypeApproximatorConfiguration.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration$IncorporationConfiguration;", "Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration$AbstractCapturedTypesApproximation;", "()V", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class IncorporationConfiguration extends AbstractCapturedTypesApproximation {
        public static final IncorporationConfiguration INSTANCE = new IncorporationConfiguration();

        private IncorporationConfiguration() {
            super(CaptureStatus.FOR_INCORPORATION, null);
        }
    }

    /* JADX INFO: compiled from: TypeApproximatorConfiguration.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration$SubtypeCapturedTypesApproximation;", "Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration$AbstractCapturedTypesApproximation;", "()V", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SubtypeCapturedTypesApproximation extends AbstractCapturedTypesApproximation {
        public static final SubtypeCapturedTypesApproximation INSTANCE = new SubtypeCapturedTypesApproximation();

        private SubtypeCapturedTypesApproximation() {
            super(CaptureStatus.FOR_SUBTYPING, null);
        }
    }

    /* JADX INFO: compiled from: TypeApproximatorConfiguration.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006R\u0014\u0010\t\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u0006¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration$InternalTypesApproximation;", "Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration$AbstractCapturedTypesApproximation;", "()V", "integerConstantOperatorType", "", "getIntegerConstantOperatorType", "()Z", "integerLiteralConstantType", "getIntegerLiteralConstantType", "intersectionTypesInContravariantPositions", "getIntersectionTypesInContravariantPositions", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class InternalTypesApproximation extends AbstractCapturedTypesApproximation {
        public static final InternalTypesApproximation INSTANCE = new InternalTypesApproximation();

        @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration
        public boolean getIntegerConstantOperatorType() {
            return true;
        }

        @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration
        public boolean getIntegerLiteralConstantType() {
            return true;
        }

        @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration
        public boolean getIntersectionTypesInContravariantPositions() {
            return true;
        }

        private InternalTypesApproximation() {
            super(CaptureStatus.FROM_EXPRESSION, null);
        }
    }

    /* JADX INFO: compiled from: TypeApproximatorConfiguration.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\t\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006R\u0014\u0010\t\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u0006R\u0014\u0010\u000b\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u0006¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration$FinalApproximationAfterResolutionAndInference;", "Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration$AbstractCapturedTypesApproximation;", "()V", "convertToNonRawVersionAfterApproximationInK2", "", "getConvertToNonRawVersionAfterApproximationInK2", "()Z", "integerConstantOperatorType", "getIntegerConstantOperatorType", "integerLiteralConstantType", "getIntegerLiteralConstantType", "intersectionTypesInContravariantPositions", "getIntersectionTypesInContravariantPositions", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FinalApproximationAfterResolutionAndInference extends AbstractCapturedTypesApproximation {
        public static final FinalApproximationAfterResolutionAndInference INSTANCE = new FinalApproximationAfterResolutionAndInference();

        @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration
        public boolean getConvertToNonRawVersionAfterApproximationInK2() {
            return true;
        }

        @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration
        public boolean getIntegerConstantOperatorType() {
            return true;
        }

        @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration
        public boolean getIntegerLiteralConstantType() {
            return true;
        }

        @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration
        public boolean getIntersectionTypesInContravariantPositions() {
            return true;
        }

        private FinalApproximationAfterResolutionAndInference() {
            super(CaptureStatus.FROM_EXPRESSION, null);
        }
    }

    /* JADX INFO: compiled from: TypeApproximatorConfiguration.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006R\u0014\u0010\t\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u0006¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration$TypeArgumentApproximation;", "Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration$AbstractCapturedTypesApproximation;", "()V", "integerConstantOperatorType", "", "getIntegerConstantOperatorType", "()Z", "integerLiteralConstantType", "getIntegerLiteralConstantType", "intersectionTypesInContravariantPositions", "getIntersectionTypesInContravariantPositions", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class TypeArgumentApproximation extends AbstractCapturedTypesApproximation {
        public static final TypeArgumentApproximation INSTANCE = new TypeArgumentApproximation();

        @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration
        public boolean getIntegerConstantOperatorType() {
            return true;
        }

        @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration
        public boolean getIntegerLiteralConstantType() {
            return true;
        }

        @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration
        public boolean getIntersectionTypesInContravariantPositions() {
            return true;
        }

        /* JADX WARN: Multi-variable type inference failed */
        private TypeArgumentApproximation() {
            super(null, 0 == true ? 1 : 0);
        }
    }

    /* JADX INFO: compiled from: TypeApproximatorConfiguration.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u001d\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0004H\u0010¢\u0006\u0002\b\u0018R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006R\u0014\u0010\t\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u0006R\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration$IntegerLiteralsTypesApproximation;", "Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration$AllFlexibleSameValue;", "()V", "allFlexible", "", "getAllFlexible", "()Z", "errorType", "getErrorType", "integerLiteralConstantType", "getIntegerLiteralConstantType", "intersection", "Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration$IntersectionStrategy;", "getIntersection", "()Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration$IntersectionStrategy;", "capturedType", "ctx", "Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/model/CapturedTypeMarker;", "shouldKeepTypeVariableBasedType", "marker", "Lorg/jetbrains/kotlin/types/model/TypeVariableTypeConstructorMarker;", "isK2", "shouldKeepTypeVariableBasedType$resolution_common", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class IntegerLiteralsTypesApproximation extends AllFlexibleSameValue {
        public static final IntegerLiteralsTypesApproximation INSTANCE = new IntegerLiteralsTypesApproximation();

        @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration
        public boolean capturedType(TypeSystemInferenceExtensionContext ctx, CapturedTypeMarker type) {
            Intrinsics.checkNotNullParameter(ctx, "ctx");
            Intrinsics.checkNotNullParameter(type, "type");
            return false;
        }

        @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration.AllFlexibleSameValue
        public boolean getAllFlexible() {
            return true;
        }

        @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration
        public boolean getErrorType() {
            return true;
        }

        @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration
        public boolean getIntegerLiteralConstantType() {
            return true;
        }

        @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration
        public boolean shouldKeepTypeVariableBasedType$resolution_common(TypeVariableTypeConstructorMarker marker, boolean isK2) {
            Intrinsics.checkNotNullParameter(marker, "marker");
            return true;
        }

        private IntegerLiteralsTypesApproximation() {
        }

        @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration
        public IntersectionStrategy getIntersection() {
            return IntersectionStrategy.ALLOWED;
        }
    }

    /* JADX INFO: compiled from: TypeApproximatorConfiguration.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration$UpperBoundAwareIntersectionTypeApproximator;", "Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration$AllFlexibleSameValue;", "()V", "allFlexible", "", "getAllFlexible", "()Z", "intersection", "Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration$IntersectionStrategy;", "getIntersection", "()Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration$IntersectionStrategy;", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class UpperBoundAwareIntersectionTypeApproximator extends AllFlexibleSameValue {
        public static final UpperBoundAwareIntersectionTypeApproximator INSTANCE = new UpperBoundAwareIntersectionTypeApproximator();
        private static final IntersectionStrategy intersection = IntersectionStrategy.TO_UPPER_BOUND_IF_SUPERTYPE;

        @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration.AllFlexibleSameValue
        public boolean getAllFlexible() {
            return true;
        }

        private UpperBoundAwareIntersectionTypeApproximator() {
        }

        @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration
        public IntersectionStrategy getIntersection() {
            return intersection;
        }
    }
}
