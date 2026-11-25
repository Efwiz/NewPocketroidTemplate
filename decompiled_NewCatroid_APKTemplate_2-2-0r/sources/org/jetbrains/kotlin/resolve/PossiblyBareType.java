package org.jetbrains.kotlin.resolve;

import net.bytebuddy.description.type.TypeDescription;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.types.CastDiagnosticsUtil;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeReconstructionResult;
import org.jetbrains.kotlin.types.TypeUtils;

/* JADX INFO: loaded from: classes6.dex */
public class PossiblyBareType {
    private final KotlinType actualType;
    private final TypeConstructor bareTypeConstructor;
    private final boolean nullable;

    /* JADX WARN: Code duplicated, block: B:21:0x0032  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 5) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "actualType";
        } else if (i == 2 || i == 3) {
            objArr[0] = "org/jetbrains/kotlin/resolve/PossiblyBareType";
        } else if (i == 4) {
            objArr[0] = "subjectType";
        } else if (i != 5) {
            objArr[0] = "bareTypeConstructor";
        } else {
            objArr[0] = "org/jetbrains/kotlin/resolve/PossiblyBareType";
        }
        if (i == 2) {
            objArr[1] = "getActualType";
        } else if (i == 3) {
            objArr[1] = "getBareTypeConstructor";
        } else if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/resolve/PossiblyBareType";
        } else {
            objArr[1] = "reconstruct";
        }
        if (i == 1) {
            objArr[2] = ModuleXmlParser.TYPE;
        } else if (i != 2 && i != 3) {
            if (i == 4) {
                objArr[2] = "reconstruct";
            } else if (i != 5) {
                objArr[2] = ConfigConstants.CONFIG_KEY_BARE;
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static PossiblyBareType bare(TypeConstructor typeConstructor, boolean z) {
        if (typeConstructor == null) {
            $$$reportNull$$$0(0);
        }
        return new PossiblyBareType(null, typeConstructor, z);
    }

    public static PossiblyBareType type(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(1);
        }
        return new PossiblyBareType(kotlinType, null, false);
    }

    private PossiblyBareType(KotlinType kotlinType, TypeConstructor typeConstructor, boolean z) {
        this.actualType = kotlinType;
        this.bareTypeConstructor = typeConstructor;
        this.nullable = z;
    }

    public boolean isBare() {
        return this.actualType == null;
    }

    public KotlinType getActualType() {
        KotlinType kotlinType = this.actualType;
        if (kotlinType == null) {
            $$$reportNull$$$0(2);
        }
        return kotlinType;
    }

    public TypeConstructor getBareTypeConstructor() {
        TypeConstructor typeConstructor = this.bareTypeConstructor;
        if (typeConstructor == null) {
            $$$reportNull$$$0(3);
        }
        return typeConstructor;
    }

    private boolean isBareTypeNullable() {
        return this.nullable;
    }

    public boolean isNullable() {
        return isBare() ? isBareTypeNullable() : getActualType().getIsMarkedNullable();
    }

    public PossiblyBareType makeNullable() {
        if (isBare()) {
            return isBareTypeNullable() ? this : bare(getBareTypeConstructor(), true);
        }
        return type(TypeUtils.makeNullable(getActualType()));
    }

    public TypeReconstructionResult reconstruct(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(4);
        }
        if (!isBare()) {
            return new TypeReconstructionResult(getActualType(), true);
        }
        TypeReconstructionResult typeReconstructionResultFindStaticallyKnownSubtype = CastDiagnosticsUtil.findStaticallyKnownSubtype(TypeUtils.makeNotNullable(kotlinType), getBareTypeConstructor());
        KotlinType resultingType = typeReconstructionResultFindStaticallyKnownSubtype.getResultingType();
        if (resultingType != null) {
            return new TypeReconstructionResult(TypeUtils.makeNullableAsSpecified(resultingType, isBareTypeNullable()), typeReconstructionResultFindStaticallyKnownSubtype.isAllArgumentsInferred());
        }
        if (typeReconstructionResultFindStaticallyKnownSubtype == null) {
            $$$reportNull$$$0(5);
        }
        return typeReconstructionResultFindStaticallyKnownSubtype;
    }

    public String toString() {
        if (isBare()) {
            return "bare " + this.bareTypeConstructor + (isBareTypeNullable() ? TypeDescription.Generic.OfWildcardType.SYMBOL : "");
        }
        return getActualType().toString();
    }
}
