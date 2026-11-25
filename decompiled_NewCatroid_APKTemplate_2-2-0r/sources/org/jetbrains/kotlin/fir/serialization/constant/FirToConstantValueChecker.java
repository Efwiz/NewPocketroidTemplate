package org.jetbrains.kotlin.fir.serialization.constant;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirArrayLiteral;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirGetClassCall;
import org.jetbrains.kotlin.fir.expressions.FirNamedArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirStringConcatenationCall;
import org.jetbrains.kotlin.fir.expressions.FirVarargArgumentsExpression;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirArrayOfCallTransformer;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFieldSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.types.ConstantValueKind;

/* JADX INFO: compiled from: FirToConstantValueTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\bÀ\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J\u001d\u0010\b\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010\fJ\u001d\u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u000b\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010\u0010J\u001d\u0010\u0011\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u000b\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010\u0014J)\u0010\u0015\u001a\u00020\u0002\"\u0004\b\u0000\u0010\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00160\u00182\u0006\u0010\u000b\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010\u0019J\u001d\u0010\u001a\u001a\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u000b\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010\u001dJ\u001d\u0010\u001e\u001a\u00020\u00022\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\u000b\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010!J\u001d\u0010\"\u001a\u00020\u00022\u0006\u0010#\u001a\u00020$2\u0006\u0010\u000b\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010%J\u001d\u0010&\u001a\u00020\u00022\u0006\u0010'\u001a\u00020(2\u0006\u0010\u000b\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010)J\u001d\u0010*\u001a\u00020\u00022\u0006\u0010+\u001a\u00020,2\u0006\u0010\u000b\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010-J\u001d\u0010.\u001a\u00020\u00022\u0006\u0010/\u001a\u0002002\u0006\u0010\u000b\u001a\u00020\u0003H\u0016¢\u0006\u0002\u00101J\u001d\u00102\u001a\u00020\u00022\u0006\u00103\u001a\u0002042\u0006\u0010\u000b\u001a\u00020\u0003H\u0016¢\u0006\u0002\u00105J\u001d\u00106\u001a\u00020\u00022\u0006\u00107\u001a\u0002082\u0006\u0010\u000b\u001a\u00020\u0003H\u0016¢\u0006\u0002\u00109R\u0018\u0010\u0005\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006:"}, d2 = {"Lorg/jetbrains/kotlin/fir/serialization/constant/FirToConstantValueChecker;", "Lorg/jetbrains/kotlin/fir/visitors/FirDefaultVisitor;", "", "Lorg/jetbrains/kotlin/fir/FirSession;", "()V", "supportedConstKinds", "", "Lorg/jetbrains/kotlin/types/ConstantValueKind;", "visitAnnotation", "annotation", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "data", "(Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;Lorg/jetbrains/kotlin/fir/FirSession;)Ljava/lang/Boolean;", "visitAnnotationCall", "annotationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationCall;Lorg/jetbrains/kotlin/fir/FirSession;)Ljava/lang/Boolean;", "visitArrayLiteral", "arrayLiteral", "Lorg/jetbrains/kotlin/fir/expressions/FirArrayLiteral;", "(Lorg/jetbrains/kotlin/fir/expressions/FirArrayLiteral;Lorg/jetbrains/kotlin/fir/FirSession;)Ljava/lang/Boolean;", "visitConstExpression", ExifInterface.GPS_DIRECTION_TRUE, "constExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirConstExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirConstExpression;Lorg/jetbrains/kotlin/fir/FirSession;)Ljava/lang/Boolean;", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/fir/FirElement;", "(Lorg/jetbrains/kotlin/fir/FirElement;Lorg/jetbrains/kotlin/fir/FirSession;)Ljava/lang/Boolean;", "visitFunctionCall", "functionCall", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;Lorg/jetbrains/kotlin/fir/FirSession;)Ljava/lang/Boolean;", "visitGetClassCall", "getClassCall", "Lorg/jetbrains/kotlin/fir/expressions/FirGetClassCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirGetClassCall;Lorg/jetbrains/kotlin/fir/FirSession;)Ljava/lang/Boolean;", "visitNamedArgumentExpression", "namedArgumentExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirNamedArgumentExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirNamedArgumentExpression;Lorg/jetbrains/kotlin/fir/FirSession;)Ljava/lang/Boolean;", "visitPropertyAccessExpression", "propertyAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirPropertyAccessExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirPropertyAccessExpression;Lorg/jetbrains/kotlin/fir/FirSession;)Ljava/lang/Boolean;", "visitQualifiedAccessExpression", "qualifiedAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;Lorg/jetbrains/kotlin/fir/FirSession;)Ljava/lang/Boolean;", "visitStringConcatenationCall", "stringConcatenationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirStringConcatenationCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirStringConcatenationCall;Lorg/jetbrains/kotlin/fir/FirSession;)Ljava/lang/Boolean;", "visitVarargArgumentsExpression", "varargArgumentsExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirVarargArgumentsExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirVarargArgumentsExpression;Lorg/jetbrains/kotlin/fir/FirSession;)Ljava/lang/Boolean;", "fir-serialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirToConstantValueChecker extends FirDefaultVisitor<Boolean, FirSession> {
    public static final FirToConstantValueChecker INSTANCE = new FirToConstantValueChecker();
    private static final Set<ConstantValueKind<?>> supportedConstKinds = SetsKt.setOf((Object[]) new ConstantValueKind[]{ConstantValueKind.Boolean.INSTANCE, ConstantValueKind.Char.INSTANCE, ConstantValueKind.String.INSTANCE, ConstantValueKind.Float.INSTANCE, ConstantValueKind.Double.INSTANCE, ConstantValueKind.Byte.INSTANCE, ConstantValueKind.UnsignedByte.INSTANCE, ConstantValueKind.Short.INSTANCE, ConstantValueKind.UnsignedShort.INSTANCE, ConstantValueKind.Int.INSTANCE, ConstantValueKind.UnsignedInt.INSTANCE, ConstantValueKind.Long.INSTANCE, ConstantValueKind.UnsignedLong.INSTANCE});

    private FirToConstantValueChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public Boolean visitElement(FirElement element, FirSession data) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(data, "data");
        return false;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public <T> Boolean visitConstExpression(FirConstExpression<T> constExpression, FirSession data) {
        Intrinsics.checkNotNullParameter(constExpression, "constExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        return Boolean.valueOf(supportedConstKinds.contains(constExpression.getKind()));
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public Boolean visitStringConcatenationCall(FirStringConcatenationCall stringConcatenationCall, FirSession data) {
        Intrinsics.checkNotNullParameter(stringConcatenationCall, "stringConcatenationCall");
        Intrinsics.checkNotNullParameter(data, "data");
        List<FirExpression> arguments = stringConcatenationCall.getArgumentList().getArguments();
        boolean z = true;
        if (!(arguments instanceof Collection) || !arguments.isEmpty()) {
            Iterator<T> it = arguments.iterator();
            while (it.hasNext()) {
                if (!((Boolean) ((FirExpression) it.next()).accept(this, data)).booleanValue()) {
                    z = false;
                    break;
                }
            }
        }
        return Boolean.valueOf(z);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public Boolean visitArrayLiteral(FirArrayLiteral arrayLiteral, FirSession data) {
        Intrinsics.checkNotNullParameter(arrayLiteral, "arrayLiteral");
        Intrinsics.checkNotNullParameter(data, "data");
        List<FirExpression> arguments = arrayLiteral.getArgumentList().getArguments();
        boolean z = true;
        if (!(arguments instanceof Collection) || !arguments.isEmpty()) {
            Iterator<T> it = arguments.iterator();
            while (it.hasNext()) {
                if (!((Boolean) ((FirExpression) it.next()).accept(this, data)).booleanValue()) {
                    z = false;
                    break;
                }
            }
        }
        return Boolean.valueOf(z);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public Boolean visitAnnotation(FirAnnotation annotation, FirSession data) {
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        Intrinsics.checkNotNullParameter(data, "data");
        return true;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public Boolean visitAnnotationCall(FirAnnotationCall annotationCall, FirSession data) {
        Intrinsics.checkNotNullParameter(annotationCall, "annotationCall");
        Intrinsics.checkNotNullParameter(data, "data");
        return true;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public Boolean visitGetClassCall(FirGetClassCall getClassCall, FirSession data) {
        Intrinsics.checkNotNullParameter(getClassCall, "getClassCall");
        Intrinsics.checkNotNullParameter(data, "data");
        ConeKotlinType type = ((FirResolvedTypeRef) getClassCall.getArgument().getTypeRef()).getType();
        if (type != null) {
            return Boolean.valueOf(ConstantValueUtilsKt.create(type) != null);
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeKotlinType");
    }

    /* JADX WARN: Code duplicated, block: B:10:0x002c  */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public Boolean visitQualifiedAccessExpression(FirQualifiedAccessExpression qualifiedAccessExpression, FirSession data) {
        FirRegularClassSymbol firRegularClassSymbol;
        Intrinsics.checkNotNullParameter(qualifiedAccessExpression, "qualifiedAccessExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        FirCallableSymbol<?> resolvedCallableSymbol = FirExpressionUtilKt.toResolvedCallableSymbol(qualifiedAccessExpression);
        boolean zBooleanValue = false;
        if (resolvedCallableSymbol == null) {
            return false;
        }
        if (resolvedCallableSymbol.getFir() instanceof FirEnumEntry) {
            if (resolvedCallableSymbol.getCallableId().getClassId() != null) {
                zBooleanValue = true;
            }
        } else if (!(resolvedCallableSymbol instanceof FirPropertySymbol)) {
            if (!(resolvedCallableSymbol instanceof FirFieldSymbol)) {
                if (resolvedCallableSymbol instanceof FirConstructorSymbol) {
                    ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag(resolvedCallableSymbol);
                    if (((coneClassLikeLookupTagContainingClassLookupTag == null || (firRegularClassSymbol = LookupTagUtilsKt.toFirRegularClassSymbol(coneClassLikeLookupTagContainingClassLookupTag, data)) == null) ? null : firRegularClassSymbol.getClassKind()) == ClassKind.ANNOTATION_CLASS) {
                        zBooleanValue = true;
                    }
                } else if (Intrinsics.areEqual(resolvedCallableSymbol.getCallableId().getPackageName().asString(), IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME)) {
                    FirExpression dispatchReceiver = qualifiedAccessExpression.getDispatchReceiver();
                    if (FirToConstantValueTransformerKt.constantIntrinsicCalls.contains(resolvedCallableSymbol.getCallableId().getCallableName().asString())) {
                        zBooleanValue = ((Boolean) dispatchReceiver.accept(this, data)).booleanValue();
                    }
                }
            } else {
                Modality modality = ((FirMemberDeclaration) ((FirFieldSymbol) resolvedCallableSymbol).getFir()).getStatus().getModality();
                if (modality == null || modality == Modality.FINAL) {
                    zBooleanValue = true;
                }
            }
        } else {
            zBooleanValue = ((FirMemberDeclaration) ((FirPropertySymbol) resolvedCallableSymbol).getFir()).getStatus().isConst();
        }
        return Boolean.valueOf(zBooleanValue);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public Boolean visitPropertyAccessExpression(FirPropertyAccessExpression propertyAccessExpression, FirSession data) {
        Intrinsics.checkNotNullParameter(propertyAccessExpression, "propertyAccessExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        return visitQualifiedAccessExpression((FirQualifiedAccessExpression) propertyAccessExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public Boolean visitFunctionCall(FirFunctionCall functionCall, FirSession data) {
        Intrinsics.checkNotNullParameter(functionCall, "functionCall");
        Intrinsics.checkNotNullParameter(data, "data");
        if (!FirArrayOfCallTransformer.INSTANCE.isArrayOfCall(functionCall, data)) {
            return visitQualifiedAccessExpression((FirQualifiedAccessExpression) functionCall, data);
        }
        List<FirExpression> arguments = functionCall.getArgumentList().getArguments();
        boolean z = true;
        if (!(arguments instanceof Collection) || !arguments.isEmpty()) {
            Iterator<T> it = arguments.iterator();
            while (it.hasNext()) {
                if (!((Boolean) ((FirExpression) it.next()).accept(this, data)).booleanValue()) {
                    z = false;
                    break;
                }
            }
        }
        return Boolean.valueOf(z);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public Boolean visitVarargArgumentsExpression(FirVarargArgumentsExpression varargArgumentsExpression, FirSession data) {
        Intrinsics.checkNotNullParameter(varargArgumentsExpression, "varargArgumentsExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        List<FirExpression> arguments = varargArgumentsExpression.getArguments();
        boolean z = true;
        if (!(arguments instanceof Collection) || !arguments.isEmpty()) {
            Iterator<T> it = arguments.iterator();
            while (it.hasNext()) {
                if (!((Boolean) ((FirExpression) it.next()).accept(this, data)).booleanValue()) {
                    z = false;
                    break;
                }
            }
        }
        return Boolean.valueOf(z);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public Boolean visitNamedArgumentExpression(FirNamedArgumentExpression namedArgumentExpression, FirSession data) {
        Intrinsics.checkNotNullParameter(namedArgumentExpression, "namedArgumentExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        return (Boolean) namedArgumentExpression.getExpression().accept(this, data);
    }
}
