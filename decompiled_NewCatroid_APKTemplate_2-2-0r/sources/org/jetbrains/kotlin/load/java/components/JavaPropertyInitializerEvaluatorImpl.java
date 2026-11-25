package org.jetbrains.kotlin.load.java.components;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.load.java.structure.JavaField;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.ConstantValueFactory;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: JavaPropertyInitializerEvaluatorImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J#\u0010\u0003\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0000¢\u0006\u0002\b\tJ\u001e\u0010\n\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00042\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/load/java/components/JavaPropertyInitializerEvaluatorImpl;", "Lorg/jetbrains/kotlin/load/java/components/JavaPropertyInitializerEvaluator;", "()V", "convertLiteralValue", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "value", "", "expectedType", "Lorg/jetbrains/kotlin/types/KotlinType;", "convertLiteralValue$frontend_java", "getInitializerConstant", "field", "Lorg/jetbrains/kotlin/load/java/structure/JavaField;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaPropertyInitializerEvaluatorImpl implements JavaPropertyInitializerEvaluator {
    public static final JavaPropertyInitializerEvaluatorImpl INSTANCE = new JavaPropertyInitializerEvaluatorImpl();

    private JavaPropertyInitializerEvaluatorImpl() {
    }

    @Override // org.jetbrains.kotlin.load.java.components.JavaPropertyInitializerEvaluator
    public ConstantValue<?> getInitializerConstant(JavaField field, PropertyDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(field, "field");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Object initializerValue = field.getInitializerValue();
        if (initializerValue == null) {
            return null;
        }
        JavaPropertyInitializerEvaluatorImpl javaPropertyInitializerEvaluatorImpl = INSTANCE;
        KotlinType type = descriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        return javaPropertyInitializerEvaluatorImpl.convertLiteralValue$frontend_java(initializerValue, type);
    }

    public final ConstantValue<?> convertLiteralValue$frontend_java(Object value, KotlinType expectedType) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        if (value instanceof Byte ? true : value instanceof Short ? true : value instanceof Integer ? true : value instanceof Long) {
            return ConstantValueFactory.INSTANCE.createIntegerConstantValue(((Number) value).longValue(), expectedType, false);
        }
        return ConstantValueFactory.createConstantValue$default(ConstantValueFactory.INSTANCE, value, null, 2, null);
    }
}
