package org.jetbrains.kotlin.cfg;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.psi.KtClassOrObject;

/* JADX INFO: compiled from: LeakingThisDescriptor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\bf\u0018\u00002\u00020\u0001:\u0004\u0006\u0007\b\tR\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\nÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/cfg/LeakingThisDescriptor;", "", "classOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "getClassOrObject", "()Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "NonFinalClass", "NonFinalFunction", "NonFinalProperty", "PropertyIsNull", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface LeakingThisDescriptor {
    KtClassOrObject getClassOrObject();

    /* JADX INFO: compiled from: LeakingThisDescriptor.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/cfg/LeakingThisDescriptor$PropertyIsNull;", "Lorg/jetbrains/kotlin/cfg/LeakingThisDescriptor;", "property", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "classOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "(Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;Lorg/jetbrains/kotlin/psi/KtClassOrObject;)V", "getClassOrObject", "()Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "getProperty", "()Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class PropertyIsNull implements LeakingThisDescriptor {
        private final KtClassOrObject classOrObject;
        private final PropertyDescriptor property;

        public static /* synthetic */ PropertyIsNull copy$default(PropertyIsNull propertyIsNull, PropertyDescriptor propertyDescriptor, KtClassOrObject ktClassOrObject, int i, Object obj) {
            if ((i & 1) != 0) {
                propertyDescriptor = propertyIsNull.property;
            }
            if ((i & 2) != 0) {
                ktClassOrObject = propertyIsNull.classOrObject;
            }
            return propertyIsNull.copy(propertyDescriptor, ktClassOrObject);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final PropertyDescriptor getProperty() {
            return this.property;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final KtClassOrObject getClassOrObject() {
            return this.classOrObject;
        }

        public final PropertyIsNull copy(PropertyDescriptor property, KtClassOrObject classOrObject) {
            Intrinsics.checkNotNullParameter(property, "property");
            Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
            return new PropertyIsNull(property, classOrObject);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof PropertyIsNull)) {
                return false;
            }
            PropertyIsNull propertyIsNull = (PropertyIsNull) other;
            return Intrinsics.areEqual(this.property, propertyIsNull.property) && Intrinsics.areEqual(this.classOrObject, propertyIsNull.classOrObject);
        }

        public int hashCode() {
            return (this.property.hashCode() * 31) + this.classOrObject.hashCode();
        }

        public String toString() {
            return "PropertyIsNull(property=" + this.property + ", classOrObject=" + this.classOrObject + ')';
        }

        public PropertyIsNull(PropertyDescriptor property, KtClassOrObject classOrObject) {
            Intrinsics.checkNotNullParameter(property, "property");
            Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
            this.property = property;
            this.classOrObject = classOrObject;
        }

        @Override // org.jetbrains.kotlin.cfg.LeakingThisDescriptor
        public KtClassOrObject getClassOrObject() {
            return this.classOrObject;
        }

        public final PropertyDescriptor getProperty() {
            return this.property;
        }
    }

    /* JADX INFO: compiled from: LeakingThisDescriptor.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/cfg/LeakingThisDescriptor$NonFinalClass;", "Lorg/jetbrains/kotlin/cfg/LeakingThisDescriptor;", "klass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "classOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Lorg/jetbrains/kotlin/psi/KtClassOrObject;)V", "getClassOrObject", "()Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "getKlass", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class NonFinalClass implements LeakingThisDescriptor {
        private final KtClassOrObject classOrObject;
        private final ClassDescriptor klass;

        public static /* synthetic */ NonFinalClass copy$default(NonFinalClass nonFinalClass, ClassDescriptor classDescriptor, KtClassOrObject ktClassOrObject, int i, Object obj) {
            if ((i & 1) != 0) {
                classDescriptor = nonFinalClass.klass;
            }
            if ((i & 2) != 0) {
                ktClassOrObject = nonFinalClass.classOrObject;
            }
            return nonFinalClass.copy(classDescriptor, ktClassOrObject);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final ClassDescriptor getKlass() {
            return this.klass;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final KtClassOrObject getClassOrObject() {
            return this.classOrObject;
        }

        public final NonFinalClass copy(ClassDescriptor klass, KtClassOrObject classOrObject) {
            Intrinsics.checkNotNullParameter(klass, "klass");
            Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
            return new NonFinalClass(klass, classOrObject);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof NonFinalClass)) {
                return false;
            }
            NonFinalClass nonFinalClass = (NonFinalClass) other;
            return Intrinsics.areEqual(this.klass, nonFinalClass.klass) && Intrinsics.areEqual(this.classOrObject, nonFinalClass.classOrObject);
        }

        public int hashCode() {
            return (this.klass.hashCode() * 31) + this.classOrObject.hashCode();
        }

        public String toString() {
            return "NonFinalClass(klass=" + this.klass + ", classOrObject=" + this.classOrObject + ')';
        }

        public NonFinalClass(ClassDescriptor klass, KtClassOrObject classOrObject) {
            Intrinsics.checkNotNullParameter(klass, "klass");
            Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
            this.klass = klass;
            this.classOrObject = classOrObject;
        }

        @Override // org.jetbrains.kotlin.cfg.LeakingThisDescriptor
        public KtClassOrObject getClassOrObject() {
            return this.classOrObject;
        }

        public final ClassDescriptor getKlass() {
            return this.klass;
        }
    }

    /* JADX INFO: compiled from: LeakingThisDescriptor.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/cfg/LeakingThisDescriptor$NonFinalProperty;", "Lorg/jetbrains/kotlin/cfg/LeakingThisDescriptor;", "property", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "classOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "(Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;Lorg/jetbrains/kotlin/psi/KtClassOrObject;)V", "getClassOrObject", "()Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "getProperty", "()Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class NonFinalProperty implements LeakingThisDescriptor {
        private final KtClassOrObject classOrObject;
        private final PropertyDescriptor property;

        public static /* synthetic */ NonFinalProperty copy$default(NonFinalProperty nonFinalProperty, PropertyDescriptor propertyDescriptor, KtClassOrObject ktClassOrObject, int i, Object obj) {
            if ((i & 1) != 0) {
                propertyDescriptor = nonFinalProperty.property;
            }
            if ((i & 2) != 0) {
                ktClassOrObject = nonFinalProperty.classOrObject;
            }
            return nonFinalProperty.copy(propertyDescriptor, ktClassOrObject);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final PropertyDescriptor getProperty() {
            return this.property;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final KtClassOrObject getClassOrObject() {
            return this.classOrObject;
        }

        public final NonFinalProperty copy(PropertyDescriptor property, KtClassOrObject classOrObject) {
            Intrinsics.checkNotNullParameter(property, "property");
            Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
            return new NonFinalProperty(property, classOrObject);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof NonFinalProperty)) {
                return false;
            }
            NonFinalProperty nonFinalProperty = (NonFinalProperty) other;
            return Intrinsics.areEqual(this.property, nonFinalProperty.property) && Intrinsics.areEqual(this.classOrObject, nonFinalProperty.classOrObject);
        }

        public int hashCode() {
            return (this.property.hashCode() * 31) + this.classOrObject.hashCode();
        }

        public String toString() {
            return "NonFinalProperty(property=" + this.property + ", classOrObject=" + this.classOrObject + ')';
        }

        public NonFinalProperty(PropertyDescriptor property, KtClassOrObject classOrObject) {
            Intrinsics.checkNotNullParameter(property, "property");
            Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
            this.property = property;
            this.classOrObject = classOrObject;
        }

        @Override // org.jetbrains.kotlin.cfg.LeakingThisDescriptor
        public KtClassOrObject getClassOrObject() {
            return this.classOrObject;
        }

        public final PropertyDescriptor getProperty() {
            return this.property;
        }
    }

    /* JADX INFO: compiled from: LeakingThisDescriptor.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/cfg/LeakingThisDescriptor$NonFinalFunction;", "Lorg/jetbrains/kotlin/cfg/LeakingThisDescriptor;", "function", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "classOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "(Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;Lorg/jetbrains/kotlin/psi/KtClassOrObject;)V", "getClassOrObject", "()Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "getFunction", "()Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class NonFinalFunction implements LeakingThisDescriptor {
        private final KtClassOrObject classOrObject;
        private final FunctionDescriptor function;

        public static /* synthetic */ NonFinalFunction copy$default(NonFinalFunction nonFinalFunction, FunctionDescriptor functionDescriptor, KtClassOrObject ktClassOrObject, int i, Object obj) {
            if ((i & 1) != 0) {
                functionDescriptor = nonFinalFunction.function;
            }
            if ((i & 2) != 0) {
                ktClassOrObject = nonFinalFunction.classOrObject;
            }
            return nonFinalFunction.copy(functionDescriptor, ktClassOrObject);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final FunctionDescriptor getFunction() {
            return this.function;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final KtClassOrObject getClassOrObject() {
            return this.classOrObject;
        }

        public final NonFinalFunction copy(FunctionDescriptor function, KtClassOrObject classOrObject) {
            Intrinsics.checkNotNullParameter(function, "function");
            Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
            return new NonFinalFunction(function, classOrObject);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof NonFinalFunction)) {
                return false;
            }
            NonFinalFunction nonFinalFunction = (NonFinalFunction) other;
            return Intrinsics.areEqual(this.function, nonFinalFunction.function) && Intrinsics.areEqual(this.classOrObject, nonFinalFunction.classOrObject);
        }

        public int hashCode() {
            return (this.function.hashCode() * 31) + this.classOrObject.hashCode();
        }

        public String toString() {
            return "NonFinalFunction(function=" + this.function + ", classOrObject=" + this.classOrObject + ')';
        }

        public NonFinalFunction(FunctionDescriptor function, KtClassOrObject classOrObject) {
            Intrinsics.checkNotNullParameter(function, "function");
            Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
            this.function = function;
            this.classOrObject = classOrObject;
        }

        @Override // org.jetbrains.kotlin.cfg.LeakingThisDescriptor
        public KtClassOrObject getClassOrObject() {
            return this.classOrObject;
        }

        public final FunctionDescriptor getFunction() {
            return this.function;
        }
    }
}
