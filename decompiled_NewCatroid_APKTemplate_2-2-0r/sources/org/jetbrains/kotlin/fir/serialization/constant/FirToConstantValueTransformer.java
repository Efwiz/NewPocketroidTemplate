package org.jetbrains.kotlin.fir.serialization.constant;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.constant.AnnotationValue;
import org.jetbrains.kotlin.constant.ArrayValue;
import org.jetbrains.kotlin.constant.BooleanValue;
import org.jetbrains.kotlin.constant.ByteValue;
import org.jetbrains.kotlin.constant.CharValue;
import org.jetbrains.kotlin.constant.ConstantValue;
import org.jetbrains.kotlin.constant.DoubleValue;
import org.jetbrains.kotlin.constant.EnumValue;
import org.jetbrains.kotlin.constant.FloatValue;
import org.jetbrains.kotlin.constant.IntValue;
import org.jetbrains.kotlin.constant.LongValue;
import org.jetbrains.kotlin.constant.NullValue;
import org.jetbrains.kotlin.constant.ShortValue;
import org.jetbrains.kotlin.constant.StringValue;
import org.jetbrains.kotlin.constant.UByteValue;
import org.jetbrains.kotlin.constant.UIntValue;
import org.jetbrains.kotlin.constant.ULongValue;
import org.jetbrains.kotlin.constant.UShortValue;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirArgumentList;
import org.jetbrains.kotlin.fir.expressions.FirArrayLiteral;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirGetClassCall;
import org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirStringConcatenationCall;
import org.jetbrains.kotlin.fir.expressions.FirVarargArgumentsExpression;
import org.jetbrains.kotlin.fir.expressions.FirWrappedArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.impl.FirResolvedArgumentList;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirArrayOfCallTransformer;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.ConstantValueKind;

/* JADX INFO: compiled from: FirToConstantValueTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÀ\u0002\u0018\u00002\u0014\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J\u001c\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00022\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u001c\u0010\t\u001a\u0006\u0012\u0002\b\u00030\u00022\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u001c\u0010\f\u001a\u0006\u0012\u0002\b\u00030\u00022\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\b\u001a\u00020\u0003H\u0016J*\u0010\u000f\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0002\"\u0004\b\u0000\u0010\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00100\u00122\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u001e\u0010\u0013\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00022\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u001e\u0010\u0016\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00022\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u001e\u0010\u0019\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00022\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u001e\u0010\u001c\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00022\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u001e\u0010\u001f\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00022\u0006\u0010 \u001a\u00020!2\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u001e\u0010\"\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00022\u0006\u0010#\u001a\u00020$2\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u001c\u0010%\u001a\u0006\u0012\u0002\b\u00030\u00022\u0006\u0010&\u001a\u00020'2\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u001e\u0010(\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00022\u0006\u0010)\u001a\u00020*2\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u001a\u0010+\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0002*\u00020,2\u0006\u0010\b\u001a\u00020\u0003H\u0002¨\u0006-"}, d2 = {"Lorg/jetbrains/kotlin/fir/serialization/constant/FirToConstantValueTransformer;", "Lorg/jetbrains/kotlin/fir/visitors/FirDefaultVisitor;", "Lorg/jetbrains/kotlin/constant/ConstantValue;", "Lorg/jetbrains/kotlin/fir/serialization/constant/FirToConstantValueTransformerData;", "()V", "visitAnnotation", "annotation", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "data", "visitAnnotationCall", "annotationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationCall;", "visitArrayLiteral", "arrayLiteral", "Lorg/jetbrains/kotlin/fir/expressions/FirArrayLiteral;", "visitConstExpression", ExifInterface.GPS_DIRECTION_TRUE, "constExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirConstExpression;", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/fir/FirElement;", "visitFunctionCall", "functionCall", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "visitGetClassCall", "getClassCall", "Lorg/jetbrains/kotlin/fir/expressions/FirGetClassCall;", "visitPropertyAccessExpression", "propertyAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirPropertyAccessExpression;", "visitQualifiedAccessExpression", "qualifiedAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "visitStringConcatenationCall", "stringConcatenationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirStringConcatenationCall;", "visitVarargArgumentsExpression", "varargArgumentsExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirVarargArgumentsExpression;", "visitWrappedArgumentExpression", "wrappedArgumentExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWrappedArgumentExpression;", "toConstantValue", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "fir-serialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirToConstantValueTransformer extends FirDefaultVisitor<ConstantValue<?>, FirToConstantValueTransformerData> {
    public static final FirToConstantValueTransformer INSTANCE = new FirToConstantValueTransformer();

    private FirToConstantValueTransformer() {
    }

    private final ConstantValue<?> toConstantValue(FirExpression firExpression, FirToConstantValueTransformerData firToConstantValueTransformerData) {
        ConstantValue<?> constantValueFindConstantValueFor;
        ConstValueProvider constValueProvider = firToConstantValueTransformerData.getConstValueProvider();
        return (constValueProvider == null || (constantValueFindConstantValueFor = constValueProvider.findConstantValueFor(firExpression)) == null) ? (ConstantValue) firExpression.accept(this, firToConstantValueTransformerData) : constantValueFindConstantValueFor;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public ConstantValue<?> visitElement(FirElement element, FirToConstantValueTransformerData data) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(data, "data");
        throw new IllegalStateException(("Illegal element as annotation argument: " + Reflection.getOrCreateKotlinClass(element.getClass()).getQualifiedName() + " -> " + UtilsKt.render(element)).toString());
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public <T> ConstantValue<?> visitConstExpression(FirConstExpression<T> constExpression, FirToConstantValueTransformerData data) {
        Intrinsics.checkNotNullParameter(constExpression, "constExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        T value = constExpression.getValue();
        ConstantValueKind<T> kind = constExpression.getKind();
        if (Intrinsics.areEqual(kind, ConstantValueKind.Boolean.INSTANCE)) {
            Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Boolean");
            return new BooleanValue(((Boolean) value).booleanValue());
        }
        if (Intrinsics.areEqual(kind, ConstantValueKind.Char.INSTANCE)) {
            Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Char");
            return new CharValue(((Character) value).charValue());
        }
        if (Intrinsics.areEqual(kind, ConstantValueKind.Byte.INSTANCE)) {
            Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Number");
            return new ByteValue(((Number) value).byteValue());
        }
        if (Intrinsics.areEqual(kind, ConstantValueKind.UnsignedByte.INSTANCE)) {
            Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Number");
            return new UByteValue(((Number) value).byteValue());
        }
        if (Intrinsics.areEqual(kind, ConstantValueKind.Short.INSTANCE)) {
            Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Number");
            return new ShortValue(((Number) value).shortValue());
        }
        if (Intrinsics.areEqual(kind, ConstantValueKind.UnsignedShort.INSTANCE)) {
            Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Number");
            return new UShortValue(((Number) value).shortValue());
        }
        if (Intrinsics.areEqual(kind, ConstantValueKind.Int.INSTANCE)) {
            Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Number");
            return new IntValue(((Number) value).intValue());
        }
        if (Intrinsics.areEqual(kind, ConstantValueKind.UnsignedInt.INSTANCE)) {
            Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Number");
            return new UIntValue(((Number) value).intValue());
        }
        if (Intrinsics.areEqual(kind, ConstantValueKind.Long.INSTANCE)) {
            Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Number");
            return new LongValue(((Number) value).longValue());
        }
        if (Intrinsics.areEqual(kind, ConstantValueKind.UnsignedLong.INSTANCE)) {
            Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Number");
            return new ULongValue(((Number) value).longValue());
        }
        if (Intrinsics.areEqual(kind, ConstantValueKind.String.INSTANCE)) {
            Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.String");
            return new StringValue((String) value);
        }
        if (Intrinsics.areEqual(kind, ConstantValueKind.Float.INSTANCE)) {
            Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Number");
            return new FloatValue(((Number) value).floatValue());
        }
        if (Intrinsics.areEqual(kind, ConstantValueKind.Double.INSTANCE)) {
            Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Number");
            return new DoubleValue(((Number) value).doubleValue());
        }
        if (Intrinsics.areEqual(kind, ConstantValueKind.Null.INSTANCE)) {
            return NullValue.INSTANCE;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public ConstantValue<?> visitStringConcatenationCall(FirStringConcatenationCall stringConcatenationCall, FirToConstantValueTransformerData data) {
        Intrinsics.checkNotNullParameter(stringConcatenationCall, "stringConcatenationCall");
        Intrinsics.checkNotNullParameter(data, "data");
        List<FirExpression> arguments = stringConcatenationCall.getArgumentList().getArguments();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(arguments, 10));
        Iterator<T> it = arguments.iterator();
        while (it.hasNext()) {
            arrayList.add(INSTANCE.toConstantValue((FirExpression) it.next(), data));
        }
        ArrayList<ConstantValue> arrayList2 = arrayList;
        if (!(arrayList2 instanceof Collection) || !arrayList2.isEmpty()) {
            for (ConstantValue constantValue : arrayList2) {
                if (constantValue == null || !(constantValue instanceof StringValue)) {
                    return null;
                }
            }
        }
        return new StringValue(CollectionsKt.joinToString$default(arrayList2, "", null, null, 0, null, new Function1<ConstantValue<?>, CharSequence>() { // from class: org.jetbrains.kotlin.fir.serialization.constant.FirToConstantValueTransformer.visitStringConcatenationCall.2
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(ConstantValue<?> constantValue2) {
                Intrinsics.checkNotNull(constantValue2, "null cannot be cast to non-null type org.jetbrains.kotlin.constant.StringValue");
                return ((StringValue) constantValue2).getValue();
            }
        }, 30, null));
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public ConstantValue<?> visitArrayLiteral(FirArrayLiteral arrayLiteral, FirToConstantValueTransformerData data) {
        Intrinsics.checkNotNullParameter(arrayLiteral, "arrayLiteral");
        Intrinsics.checkNotNullParameter(data, "data");
        List<FirExpression> arguments = arrayLiteral.getArgumentList().getArguments();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = arguments.iterator();
        while (it.hasNext()) {
            ConstantValue<?> constantValue = INSTANCE.toConstantValue((FirExpression) it.next(), data);
            if (constantValue != null) {
                arrayList.add(constantValue);
            }
        }
        return new ArrayValue(arrayList);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public ConstantValue<?> visitAnnotation(FirAnnotation annotation, FirToConstantValueTransformerData data) {
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        Intrinsics.checkNotNullParameter(data, "data");
        Map<Name, ConstantValue<?>> mapConvertToConstantValues = ConstantValueUtilsKt.convertToConstantValues(annotation.getArgumentMapping().getMapping(), data.getSession(), data.getConstValueProvider());
        FirReference reference = FirExpressionUtilKt.toReference(annotation);
        return AnnotationValue.INSTANCE.create(FirTypeUtilsKt.getConeType(annotation.getAnnotationTypeRef()), ConstantValueUtilsKt.addEmptyVarargValuesFor(mapConvertToConstantValues, reference != null ? FirReferenceUtilsKt.toResolvedFunctionSymbol$default(reference, false, 1, null) : null));
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public ConstantValue<?> visitAnnotationCall(FirAnnotationCall annotationCall, FirToConstantValueTransformerData data) {
        Intrinsics.checkNotNullParameter(annotationCall, "annotationCall");
        Intrinsics.checkNotNullParameter(data, "data");
        return visitAnnotation((FirAnnotation) annotationCall, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public ConstantValue<?> visitGetClassCall(FirGetClassCall getClassCall, FirToConstantValueTransformerData data) {
        Intrinsics.checkNotNullParameter(getClassCall, "getClassCall");
        Intrinsics.checkNotNullParameter(data, "data");
        ConeKotlinType type = ((FirResolvedTypeRef) getClassCall.getArgument().getTypeRef()).getType();
        if (type != null) {
            return ConstantValueUtilsKt.create(type);
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeKotlinType");
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public ConstantValue<?> visitQualifiedAccessExpression(FirQualifiedAccessExpression qualifiedAccessExpression, FirToConstantValueTransformerData data) {
        ConstantValue<?> constantValue;
        FirRegularClassSymbol firRegularClassSymbol;
        Map<Name, ConstantValue<?>> mapConvertToConstantValues;
        Map<Name, ConstantValue<Object>> mapAddEmptyVarargValuesFor;
        FirExpression initializer;
        FirExpression initializer2;
        Intrinsics.checkNotNullParameter(qualifiedAccessExpression, "qualifiedAccessExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        FirCallableSymbol<?> resolvedCallableSymbol = FirExpressionUtilKt.toResolvedCallableSymbol(qualifiedAccessExpression);
        if (resolvedCallableSymbol == null) {
            return null;
        }
        FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) resolvedCallableSymbol.getFir();
        if (resolvedCallableSymbol.getFir() instanceof FirEnumEntry) {
            ClassId classId = resolvedCallableSymbol.getCallableId().getClassId();
            if (classId == null) {
                return null;
            }
            Object fir = resolvedCallableSymbol.getFir();
            Intrinsics.checkNotNull(fir, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirEnumEntry");
            return new EnumValue(classId, ((FirEnumEntry) fir).getName());
        }
        if (resolvedCallableSymbol instanceof FirPropertySymbol) {
            FirPropertySymbol firPropertySymbol = (FirPropertySymbol) resolvedCallableSymbol;
            if (!((FirMemberDeclaration) firPropertySymbol.getFir()).getStatus().isConst() || (initializer2 = ((FirProperty) firPropertySymbol.getFir()).getInitializer()) == null) {
                return null;
            }
            return toConstantValue(initializer2, data);
        }
        if (firCallableDeclaration instanceof FirField) {
            Modality modality = firCallableDeclaration.getStatus().getModality();
            if (!(modality == null || modality == Modality.FINAL) || (initializer = ((FirField) firCallableDeclaration).getInitializer()) == null) {
                return null;
            }
            return toConstantValue(initializer, data);
        }
        if (resolvedCallableSymbol instanceof FirConstructorSymbol) {
            FirFunctionCall firFunctionCall = (FirFunctionCall) qualifiedAccessExpression;
            ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag(resolvedCallableSymbol);
            if (coneClassLikeLookupTagContainingClassLookupTag == null || (firRegularClassSymbol = LookupTagUtilsKt.toFirRegularClassSymbol(coneClassLikeLookupTagContainingClassLookupTag, data.getSession())) == null || firRegularClassSymbol.getClassKind() != ClassKind.ANNOTATION_CLASS) {
                return null;
            }
            FirArgumentList argumentList = firFunctionCall.getArgumentList();
            LinkedHashMap<FirExpression, FirValueParameter> mapping = argumentList instanceof FirResolvedArgumentList ? ((FirResolvedArgumentList) argumentList).getMapping() : null;
            if (mapping != null && (mapConvertToConstantValues = ConstantValueUtilsKt.convertToConstantValues(mapping, data.getSession(), data.getConstValueProvider())) != null && (mapAddEmptyVarargValuesFor = ConstantValueUtilsKt.addEmptyVarargValuesFor(mapConvertToConstantValues, (FirFunctionSymbol) resolvedCallableSymbol)) != null) {
                return AnnotationValue.INSTANCE.create(FirTypeUtilsKt.getConeType(qualifiedAccessExpression.getTypeRef()), mapAddEmptyVarargValuesFor);
            }
            return null;
        }
        if (!Intrinsics.areEqual(resolvedCallableSymbol.getCallableId().getPackageName().asString(), IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME)) {
            return null;
        }
        String strAsString = resolvedCallableSymbol.getCallableId().getCallableName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        if (!FirToConstantValueTransformerKt.constantIntrinsicCalls.contains(strAsString) || (constantValue = toConstantValue(qualifiedAccessExpression.getDispatchReceiver(), data)) == null) {
            return null;
        }
        switch (strAsString.hashCode()) {
            case -1177945951:
                if (!strAsString.equals("toFloat")) {
                    return null;
                }
                Object value = constantValue.getValue();
                Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Number");
                return new FloatValue(((Number) value).floatValue());
            case -1166058815:
                if (!strAsString.equals("toShort")) {
                    return null;
                }
                Object value2 = constantValue.getValue();
                Intrinsics.checkNotNull(value2, "null cannot be cast to non-null type kotlin.Number");
                return new ShortValue(((Number) value2).shortValue());
            case -869388765:
                if (!strAsString.equals("toByte")) {
                    return null;
                }
                Object value3 = constantValue.getValue();
                Intrinsics.checkNotNull(value3, "null cannot be cast to non-null type kotlin.Number");
                return new ByteValue(((Number) value3).byteValue());
            case -869375887:
                if (!strAsString.equals("toChar")) {
                    return null;
                }
                Object value4 = constantValue.getValue();
                Intrinsics.checkNotNull(value4, "null cannot be cast to non-null type kotlin.Number");
                return new CharValue((char) ((Number) value4).intValue());
            case -869100649:
                if (!strAsString.equals("toLong")) {
                    return null;
                }
                Object value5 = constantValue.getValue();
                Intrinsics.checkNotNull(value5, "null cannot be cast to non-null type kotlin.Number");
                return new LongValue(((Number) value5).longValue());
            case 414819457:
                if (!strAsString.equals("unaryMinus")) {
                    return null;
                }
                if (constantValue instanceof ByteValue) {
                    return new ByteValue((byte) (-((ByteValue) constantValue).getValue().byteValue()));
                }
                if (constantValue instanceof LongValue) {
                    return new LongValue(-((LongValue) constantValue).getValue().longValue());
                }
                if (constantValue instanceof ShortValue) {
                    return new ShortValue((short) (-((ShortValue) constantValue).getValue().shortValue()));
                }
                if (constantValue instanceof FloatValue) {
                    return new FloatValue(-((FloatValue) constantValue).getValue().floatValue());
                }
                if (constantValue instanceof DoubleValue) {
                    return new DoubleValue(-((DoubleValue) constantValue).getValue().doubleValue());
                }
                return null;
            case 2084073004:
                if (!strAsString.equals("toDouble")) {
                    return null;
                }
                Object value6 = constantValue.getValue();
                Intrinsics.checkNotNull(value6, "null cannot be cast to non-null type kotlin.Number");
                return new DoubleValue(((Number) value6).doubleValue());
            default:
                return null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public ConstantValue<?> visitPropertyAccessExpression(FirPropertyAccessExpression propertyAccessExpression, FirToConstantValueTransformerData data) {
        Intrinsics.checkNotNullParameter(propertyAccessExpression, "propertyAccessExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        return visitQualifiedAccessExpression((FirQualifiedAccessExpression) propertyAccessExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public ConstantValue<?> visitFunctionCall(FirFunctionCall functionCall, FirToConstantValueTransformerData data) {
        Intrinsics.checkNotNullParameter(functionCall, "functionCall");
        Intrinsics.checkNotNullParameter(data, "data");
        if (FirArrayOfCallTransformer.INSTANCE.isArrayOfCall(functionCall, data.getSession())) {
            return (ConstantValue) new FirArrayOfCallTransformer().transformFunctionCall(functionCall, data.getSession()).accept(this, data);
        }
        return visitQualifiedAccessExpression((FirQualifiedAccessExpression) functionCall, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public ConstantValue<?> visitVarargArgumentsExpression(FirVarargArgumentsExpression varargArgumentsExpression, FirToConstantValueTransformerData data) {
        List<FirExpression> arguments;
        Intrinsics.checkNotNullParameter(varargArgumentsExpression, "varargArgumentsExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        List<FirExpression> arguments2 = varargArgumentsExpression.getArguments();
        FirExpression firExpression = (FirExpression) CollectionsKt.singleOrNull((List) arguments2);
        FirExpression firExpressionUnwrapArgument = firExpression != null ? FirExpressionUtilKt.unwrapArgument(firExpression) : null;
        FirArrayLiteral firArrayLiteral = firExpressionUnwrapArgument instanceof FirArrayLiteral ? (FirArrayLiteral) firExpressionUnwrapArgument : null;
        if (firArrayLiteral != null && (arguments = firArrayLiteral.getArgumentList().getArguments()) != null) {
            arguments2 = arguments;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = arguments2.iterator();
        while (it.hasNext()) {
            ConstantValue<?> constantValue = INSTANCE.toConstantValue((FirExpression) it.next(), data);
            if (constantValue != null) {
                arrayList.add(constantValue);
            }
        }
        return new ArrayValue(arrayList);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public ConstantValue<?> visitWrappedArgumentExpression(FirWrappedArgumentExpression wrappedArgumentExpression, FirToConstantValueTransformerData data) {
        Intrinsics.checkNotNullParameter(wrappedArgumentExpression, "wrappedArgumentExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        return toConstantValue(wrappedArgumentExpression.getExpression(), data);
    }
}
