package org.jetbrains.kotlin.psi.stubs.impl;

import java.io.IOException;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.contracts.description.EventOccurrencesRange;
import org.jetbrains.kotlin.contracts.description.KtBinaryLogicExpression;
import org.jetbrains.kotlin.contracts.description.KtBooleanExpression;
import org.jetbrains.kotlin.contracts.description.KtBooleanValueParameterReference;
import org.jetbrains.kotlin.contracts.description.KtCallsEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.KtConditionalEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.KtConstantReference;
import org.jetbrains.kotlin.contracts.description.KtContractDescriptionElement;
import org.jetbrains.kotlin.contracts.description.KtEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.KtIsInstancePredicate;
import org.jetbrains.kotlin.contracts.description.KtIsNullPredicate;
import org.jetbrains.kotlin.contracts.description.KtLogicalNot;
import org.jetbrains.kotlin.contracts.description.KtReturnsEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.KtValueParameterReference;
import org.jetbrains.kotlin.contracts.description.LogicOperationKind;
import org.jetbrains.kotlin.psi.stubs.elements.KtUserTypeElementType;

/* JADX INFO: compiled from: KotlinContractEffectStubImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\b\u0086\u0081\u0002\u0018\u0000 \u00132\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0013B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00042\u0006\u0010\u0007\u001a\u00020\bH&j\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinContractEffectType;", "", "(Ljava/lang/String;I)V", "deserialize", "Lorg/jetbrains/kotlin/contracts/description/KtContractDescriptionElement;", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinTypeBean;", "", "dataStream", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubInputStream;", "CALLS", "RETURNS", "CONDITIONAL", "IS_NULL", "IS_INSTANCE", "NOT", "BOOLEAN_LOGIC", "PARAMETER_REFERENCE", "BOOLEAN_PARAMETER_REFERENCE", "CONSTANT", "Companion", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum KotlinContractEffectType {
    CALLS { // from class: org.jetbrains.kotlin.psi.stubs.impl.KotlinContractEffectType.CALLS
        @Override // org.jetbrains.kotlin.psi.stubs.impl.KotlinContractEffectType
        public KtCallsEffectDeclaration deserialize(StubInputStream dataStream) {
            Intrinsics.checkNotNullParameter(dataStream, "dataStream");
            KtContractDescriptionElement ktContractDescriptionElementDeserialize = KotlinContractEffectType.PARAMETER_REFERENCE.deserialize(dataStream);
            EventOccurrencesRange eventOccurrencesRange = EventOccurrencesRange.values()[dataStream.readInt()];
            Intrinsics.checkNotNull(ktContractDescriptionElementDeserialize, "null cannot be cast to non-null type org.jetbrains.kotlin.contracts.description.KtValueParameterReference<org.jetbrains.kotlin.psi.stubs.impl.KotlinTypeBean, kotlin.Nothing?>");
            return new KtCallsEffectDeclaration((KtValueParameterReference) ktContractDescriptionElementDeserialize, eventOccurrencesRange);
        }
    },
    RETURNS { // from class: org.jetbrains.kotlin.psi.stubs.impl.KotlinContractEffectType.RETURNS
        @Override // org.jetbrains.kotlin.psi.stubs.impl.KotlinContractEffectType
        public KtContractDescriptionElement deserialize(StubInputStream dataStream) {
            Intrinsics.checkNotNullParameter(dataStream, "dataStream");
            KtContractDescriptionElement ktContractDescriptionElementDeserialize = KotlinContractEffectType.CONSTANT.deserialize(dataStream);
            Intrinsics.checkNotNull(ktContractDescriptionElementDeserialize, "null cannot be cast to non-null type org.jetbrains.kotlin.contracts.description.KtConstantReference<org.jetbrains.kotlin.psi.stubs.impl.KotlinTypeBean, kotlin.Nothing?>");
            return new KtReturnsEffectDeclaration((KtConstantReference) ktContractDescriptionElementDeserialize);
        }
    },
    CONDITIONAL { // from class: org.jetbrains.kotlin.psi.stubs.impl.KotlinContractEffectType.CONDITIONAL
        @Override // org.jetbrains.kotlin.psi.stubs.impl.KotlinContractEffectType
        public KtContractDescriptionElement deserialize(StubInputStream dataStream) {
            Intrinsics.checkNotNullParameter(dataStream, "dataStream");
            KtContractDescriptionElement ktContractDescriptionElementDeserialize = KotlinContractEffectType.values()[dataStream.readInt()].deserialize(dataStream);
            KtContractDescriptionElement ktContractDescriptionElementDeserialize2 = KotlinContractEffectType.values()[dataStream.readInt()].deserialize(dataStream);
            Intrinsics.checkNotNull(ktContractDescriptionElementDeserialize, "null cannot be cast to non-null type org.jetbrains.kotlin.contracts.description.KtEffectDeclaration<org.jetbrains.kotlin.psi.stubs.impl.KotlinTypeBean, kotlin.Nothing?>");
            Intrinsics.checkNotNull(ktContractDescriptionElementDeserialize2, "null cannot be cast to non-null type org.jetbrains.kotlin.contracts.description.KtBooleanExpression<org.jetbrains.kotlin.psi.stubs.impl.KotlinTypeBean, kotlin.Nothing?>");
            return new KtConditionalEffectDeclaration((KtEffectDeclaration) ktContractDescriptionElementDeserialize, (KtBooleanExpression) ktContractDescriptionElementDeserialize2);
        }
    },
    IS_NULL { // from class: org.jetbrains.kotlin.psi.stubs.impl.KotlinContractEffectType.IS_NULL
        @Override // org.jetbrains.kotlin.psi.stubs.impl.KotlinContractEffectType
        public KtContractDescriptionElement deserialize(StubInputStream dataStream) {
            Intrinsics.checkNotNullParameter(dataStream, "dataStream");
            KtContractDescriptionElement ktContractDescriptionElementDeserialize = KotlinContractEffectType.PARAMETER_REFERENCE.deserialize(dataStream);
            Intrinsics.checkNotNull(ktContractDescriptionElementDeserialize, "null cannot be cast to non-null type org.jetbrains.kotlin.contracts.description.KtValueParameterReference<org.jetbrains.kotlin.psi.stubs.impl.KotlinTypeBean, kotlin.Nothing?>");
            return new KtIsNullPredicate((KtValueParameterReference) ktContractDescriptionElementDeserialize, dataStream.readBoolean());
        }
    },
    IS_INSTANCE { // from class: org.jetbrains.kotlin.psi.stubs.impl.KotlinContractEffectType.IS_INSTANCE
        @Override // org.jetbrains.kotlin.psi.stubs.impl.KotlinContractEffectType
        public KtContractDescriptionElement deserialize(StubInputStream dataStream) throws IOException {
            Intrinsics.checkNotNullParameter(dataStream, "dataStream");
            KtContractDescriptionElement ktContractDescriptionElementDeserialize = KotlinContractEffectType.PARAMETER_REFERENCE.deserialize(dataStream);
            Intrinsics.checkNotNull(ktContractDescriptionElementDeserialize, "null cannot be cast to non-null type org.jetbrains.kotlin.contracts.description.KtValueParameterReference<org.jetbrains.kotlin.psi.stubs.impl.KotlinTypeBean, kotlin.Nothing?>");
            KotlinTypeBean kotlinTypeBeanDeserializeType = KtUserTypeElementType.deserializeType(dataStream);
            Intrinsics.checkNotNull(kotlinTypeBeanDeserializeType);
            return new KtIsInstancePredicate((KtValueParameterReference) ktContractDescriptionElementDeserialize, kotlinTypeBeanDeserializeType, dataStream.readBoolean());
        }
    },
    NOT { // from class: org.jetbrains.kotlin.psi.stubs.impl.KotlinContractEffectType.NOT
        @Override // org.jetbrains.kotlin.psi.stubs.impl.KotlinContractEffectType
        public KtContractDescriptionElement deserialize(StubInputStream dataStream) {
            Intrinsics.checkNotNullParameter(dataStream, "dataStream");
            KtContractDescriptionElement ktContractDescriptionElementDeserialize = KotlinContractEffectType.values()[dataStream.readInt()].deserialize(dataStream);
            Intrinsics.checkNotNull(ktContractDescriptionElementDeserialize, "null cannot be cast to non-null type org.jetbrains.kotlin.contracts.description.KtBooleanExpression<org.jetbrains.kotlin.psi.stubs.impl.KotlinTypeBean, kotlin.Nothing?>");
            return new KtLogicalNot((KtBooleanExpression) ktContractDescriptionElementDeserialize);
        }
    },
    BOOLEAN_LOGIC { // from class: org.jetbrains.kotlin.psi.stubs.impl.KotlinContractEffectType.BOOLEAN_LOGIC
        @Override // org.jetbrains.kotlin.psi.stubs.impl.KotlinContractEffectType
        public KtContractDescriptionElement deserialize(StubInputStream dataStream) {
            Intrinsics.checkNotNullParameter(dataStream, "dataStream");
            LogicOperationKind logicOperationKind = dataStream.readBoolean() ? LogicOperationKind.AND : LogicOperationKind.OR;
            KtContractDescriptionElement ktContractDescriptionElementDeserialize = KotlinContractEffectType.values()[dataStream.readInt()].deserialize(dataStream);
            Intrinsics.checkNotNull(ktContractDescriptionElementDeserialize, "null cannot be cast to non-null type org.jetbrains.kotlin.contracts.description.KtBooleanExpression<org.jetbrains.kotlin.psi.stubs.impl.KotlinTypeBean, kotlin.Nothing?>");
            KtContractDescriptionElement ktContractDescriptionElementDeserialize2 = KotlinContractEffectType.values()[dataStream.readInt()].deserialize(dataStream);
            Intrinsics.checkNotNull(ktContractDescriptionElementDeserialize2, "null cannot be cast to non-null type org.jetbrains.kotlin.contracts.description.KtBooleanExpression<org.jetbrains.kotlin.psi.stubs.impl.KotlinTypeBean, kotlin.Nothing?>");
            return new KtBinaryLogicExpression((KtBooleanExpression) ktContractDescriptionElementDeserialize, (KtBooleanExpression) ktContractDescriptionElementDeserialize2, logicOperationKind);
        }
    },
    PARAMETER_REFERENCE { // from class: org.jetbrains.kotlin.psi.stubs.impl.KotlinContractEffectType.PARAMETER_REFERENCE
        @Override // org.jetbrains.kotlin.psi.stubs.impl.KotlinContractEffectType
        public KtValueParameterReference deserialize(StubInputStream dataStream) {
            Intrinsics.checkNotNullParameter(dataStream, "dataStream");
            return new KtValueParameterReference(dataStream.readInt(), KotlinContractEffectType.IGNORE_REFERENCE_PARAMETER_NAME);
        }
    },
    BOOLEAN_PARAMETER_REFERENCE { // from class: org.jetbrains.kotlin.psi.stubs.impl.KotlinContractEffectType.BOOLEAN_PARAMETER_REFERENCE
        @Override // org.jetbrains.kotlin.psi.stubs.impl.KotlinContractEffectType
        public KtValueParameterReference deserialize(StubInputStream dataStream) {
            Intrinsics.checkNotNullParameter(dataStream, "dataStream");
            return new KtBooleanValueParameterReference(dataStream.readInt(), KotlinContractEffectType.IGNORE_REFERENCE_PARAMETER_NAME);
        }
    },
    CONSTANT { // from class: org.jetbrains.kotlin.psi.stubs.impl.KotlinContractEffectType.CONSTANT
        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
        @Override // org.jetbrains.kotlin.psi.stubs.impl.KotlinContractEffectType
        public KtContractDescriptionElement deserialize(StubInputStream dataStream) throws IOException {
            Intrinsics.checkNotNullParameter(dataStream, "dataStream");
            String nameString = dataStream.readNameString();
            Intrinsics.checkNotNull(nameString);
            switch (nameString.hashCode()) {
                case -1906409581:
                    if (nameString.equals("NOT_NULL")) {
                        return KotlinContractConstantValues.INSTANCE.getNOT_NULL();
                    }
                    break;
                case -390388262:
                    if (nameString.equals("WILDCARD")) {
                        return KotlinContractConstantValues.INSTANCE.getWILDCARD();
                    }
                    break;
                case 2407815:
                    if (nameString.equals("NULL")) {
                        return KotlinContractConstantValues.INSTANCE.getNULL();
                    }
                    break;
                case 2583950:
                    if (nameString.equals("TRUE")) {
                        return KotlinContractConstantValues.INSTANCE.getTRUE();
                    }
                    break;
                case 66658563:
                    if (nameString.equals("FALSE")) {
                        return KotlinContractConstantValues.INSTANCE.getFALSE();
                    }
                    break;
            }
            throw new IllegalStateException(("Unexpected " + nameString).toString());
        }
    };

    public static final String IGNORE_REFERENCE_PARAMETER_NAME = "<ignore>";
    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    /* synthetic */ KotlinContractEffectType(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public static EnumEntries<KotlinContractEffectType> getEntries() {
        return $ENTRIES;
    }

    public abstract KtContractDescriptionElement deserialize(StubInputStream dataStream);
}
