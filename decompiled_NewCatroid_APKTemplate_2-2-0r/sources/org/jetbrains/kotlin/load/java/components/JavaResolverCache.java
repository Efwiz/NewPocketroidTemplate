package org.jetbrains.kotlin.load.java.components;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaElement;
import org.jetbrains.kotlin.load.java.structure.JavaField;
import org.jetbrains.kotlin.load.java.structure.JavaMember;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: loaded from: classes5.dex */
public interface JavaResolverCache {
    public static final JavaResolverCache EMPTY = new JavaResolverCache() { // from class: org.jetbrains.kotlin.load.java.components.JavaResolverCache.1
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            switch (i) {
                case 1:
                    objArr[0] = "member";
                    break;
                case 2:
                case 4:
                case 6:
                case 8:
                    objArr[0] = "descriptor";
                    break;
                case 3:
                    objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                    break;
                case 5:
                    objArr[0] = "field";
                    break;
                case 7:
                    objArr[0] = "javaClass";
                    break;
                default:
                    objArr[0] = "fqName";
                    break;
            }
            objArr[1] = "org/jetbrains/kotlin/load/java/components/JavaResolverCache$1";
            switch (i) {
                case 1:
                case 2:
                    objArr[2] = "recordMethod";
                    break;
                case 3:
                case 4:
                    objArr[2] = "recordConstructor";
                    break;
                case 5:
                case 6:
                    objArr[2] = "recordField";
                    break;
                case 7:
                case 8:
                    objArr[2] = "recordClass";
                    break;
                default:
                    objArr[2] = "getClassResolvedFromSource";
                    break;
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        @Override // org.jetbrains.kotlin.load.java.components.JavaResolverCache
        public ClassDescriptor getClassResolvedFromSource(FqName fqName) {
            if (fqName != null) {
                return null;
            }
            $$$reportNull$$$0(0);
            return null;
        }

        @Override // org.jetbrains.kotlin.load.java.components.JavaResolverCache
        public void recordClass(JavaClass javaClass, ClassDescriptor classDescriptor) {
            if (javaClass == null) {
                $$$reportNull$$$0(7);
            }
            if (classDescriptor == null) {
                $$$reportNull$$$0(8);
            }
        }

        @Override // org.jetbrains.kotlin.load.java.components.JavaResolverCache
        public void recordConstructor(JavaElement javaElement, ConstructorDescriptor constructorDescriptor) {
            if (javaElement == null) {
                $$$reportNull$$$0(3);
            }
            if (constructorDescriptor == null) {
                $$$reportNull$$$0(4);
            }
        }

        @Override // org.jetbrains.kotlin.load.java.components.JavaResolverCache
        public void recordField(JavaField javaField, PropertyDescriptor propertyDescriptor) {
            if (javaField == null) {
                $$$reportNull$$$0(5);
            }
            if (propertyDescriptor == null) {
                $$$reportNull$$$0(6);
            }
        }

        @Override // org.jetbrains.kotlin.load.java.components.JavaResolverCache
        public void recordMethod(JavaMember javaMember, SimpleFunctionDescriptor simpleFunctionDescriptor) {
            if (javaMember == null) {
                $$$reportNull$$$0(1);
            }
            if (simpleFunctionDescriptor == null) {
                $$$reportNull$$$0(2);
            }
        }
    };

    ClassDescriptor getClassResolvedFromSource(FqName fqName);

    void recordClass(JavaClass javaClass, ClassDescriptor classDescriptor);

    void recordConstructor(JavaElement javaElement, ConstructorDescriptor constructorDescriptor);

    void recordField(JavaField javaField, PropertyDescriptor propertyDescriptor);

    void recordMethod(JavaMember javaMember, SimpleFunctionDescriptor simpleFunctionDescriptor);
}
