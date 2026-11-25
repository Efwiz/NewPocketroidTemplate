package org.jetbrains.kotlin.ir.types.impl;

import java.util.List;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeAbbreviation;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.SimpleTypeNullability;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.model.CaptureStatus;
import org.jetbrains.kotlin.types.model.CapturedTypeConstructorMarker;
import org.jetbrains.kotlin.types.model.CapturedTypeMarker;

/* JADX INFO: compiled from: IrTypeBase.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002:\u00011B'\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u0013\u0010+\u001a\u00020,2\b\u0010-\u001a\u0004\u0018\u00010.H\u0096\u0002J\b\u0010/\u001a\u000200H\u0016R\u0016\u0010\f\u001a\u0004\u0018\u00010\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\b0\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0014R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0019\u001a\u00020\u001a8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u001d\u001a\u00020\u001e¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0014\u0010#\u001a\u00020$8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b%\u0010&R\u0014\u0010'\u001a\u00020(8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b)\u0010*¨\u00062"}, d2 = {"Lorg/jetbrains/kotlin/ir/types/impl/IrCapturedType;", "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "Lorg/jetbrains/kotlin/types/model/CapturedTypeMarker;", "captureStatus", "Lorg/jetbrains/kotlin/types/model/CaptureStatus;", "lowerType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "projection", "Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "typeParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "(Lorg/jetbrains/kotlin/types/model/CaptureStatus;Lorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;)V", "abbreviation", "Lorg/jetbrains/kotlin/ir/types/IrTypeAbbreviation;", "getAbbreviation", "()Lorg/jetbrains/kotlin/ir/types/IrTypeAbbreviation;", "annotations", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "getAnnotations", "()Ljava/util/List;", "arguments", "getArguments", "getCaptureStatus", "()Lorg/jetbrains/kotlin/types/model/CaptureStatus;", "classifier", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "getClassifier", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/ir/types/impl/IrCapturedType$Constructor;", "getConstructor", "()Lorg/jetbrains/kotlin/ir/types/impl/IrCapturedType$Constructor;", "getLowerType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "nullability", "Lorg/jetbrains/kotlin/ir/types/SimpleTypeNullability;", "getNullability", "()Lorg/jetbrains/kotlin/ir/types/SimpleTypeNullability;", "variance", "Lorg/jetbrains/kotlin/types/Variance;", "getVariance", "()Lorg/jetbrains/kotlin/types/Variance;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "Constructor", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrCapturedType extends IrSimpleType implements CapturedTypeMarker {
    private final CaptureStatus captureStatus;
    private final Constructor constructor;
    private final IrType lowerType;

    @Override // org.jetbrains.kotlin.ir.types.IrSimpleType
    public IrTypeAbbreviation getAbbreviation() {
        return null;
    }

    public final CaptureStatus getCaptureStatus() {
        return this.captureStatus;
    }

    public final IrType getLowerType() {
        return this.lowerType;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrCapturedType(CaptureStatus captureStatus, IrType irType, IrTypeArgument projection, IrTypeParameter typeParameter) {
        super(null);
        Intrinsics.checkNotNullParameter(captureStatus, "captureStatus");
        Intrinsics.checkNotNullParameter(projection, "projection");
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        this.captureStatus = captureStatus;
        this.lowerType = irType;
        this.constructor = new Constructor(projection, typeParameter);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeProjection
    public Variance getVariance() {
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }

    /* JADX INFO: compiled from: IrTypeBase.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0014\u0010\u0011\u001a\u00020\u00122\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\bR\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\b8F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/ir/types/impl/IrCapturedType$Constructor;", "Lorg/jetbrains/kotlin/types/model/CapturedTypeConstructorMarker;", "argument", "Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "typeParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "(Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;)V", "_superTypes", "", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getArgument", "()Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "superTypes", "getSuperTypes", "()Ljava/util/List;", "getTypeParameter", "()Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "initSuperTypes", "", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Constructor implements CapturedTypeConstructorMarker {
        private List<? extends IrType> _superTypes;
        private final IrTypeArgument argument;
        private final IrTypeParameter typeParameter;

        public Constructor(IrTypeArgument argument, IrTypeParameter typeParameter) {
            Intrinsics.checkNotNullParameter(argument, "argument");
            Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
            this.argument = argument;
            this.typeParameter = typeParameter;
            this._superTypes = CollectionsKt.emptyList();
        }

        public final IrTypeArgument getArgument() {
            return this.argument;
        }

        public final IrTypeParameter getTypeParameter() {
            return this.typeParameter;
        }

        public final List<IrType> getSuperTypes() {
            return this._superTypes;
        }

        public final void initSuperTypes(List<? extends IrType> superTypes) {
            Intrinsics.checkNotNullParameter(superTypes, "superTypes");
            this._superTypes = superTypes;
        }
    }

    public final Constructor getConstructor() {
        return this.constructor;
    }

    @Override // org.jetbrains.kotlin.ir.types.IrSimpleType
    public IrClassifierSymbol getClassifier() {
        throw new IllegalStateException("Captured Type does not have a classifier".toString());
    }

    @Override // org.jetbrains.kotlin.ir.types.IrSimpleType
    public List<IrTypeArgument> getArguments() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.ir.types.IrSimpleType
    public SimpleTypeNullability getNullability() {
        return SimpleTypeNullability.DEFINITELY_NOT_NULL;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer
    public List<IrConstructorCall> getAnnotations() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.ir.types.IrType, org.jetbrains.kotlin.ir.types.IrTypeArgument
    public boolean equals(Object other) {
        if (other instanceof IrCapturedType) {
            IrCapturedType irCapturedType = (IrCapturedType) other;
            if (this.captureStatus == irCapturedType.captureStatus && Intrinsics.areEqual(this.lowerType, irCapturedType.lowerType) && this.constructor == irCapturedType.constructor) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.ir.types.IrType, org.jetbrains.kotlin.ir.types.IrTypeArgument
    public int hashCode() {
        int iHashCode = this.captureStatus.hashCode() * 31;
        IrType irType = this.lowerType;
        return ((iHashCode + (irType != null ? irType.hashCode() : 0)) * 31) + this.constructor.hashCode();
    }
}
