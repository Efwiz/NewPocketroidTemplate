package org.jetbrains.kotlin.load.java.structure.impl;

import java.util.Collection;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiWildcardType;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementSourceFactory;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementTypeSource;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaFixedElementSourceFactory;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaSourceFactoryOwner;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: loaded from: classes5.dex */
public abstract class JavaTypeImpl<Psi extends PsiType> implements JavaType, JavaAnnotationOwnerImpl, JavaSourceFactoryOwner {
    private final JavaElementTypeSource<Psi> psiType;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 4 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 4 || i == 5) ? 2 : 3];
        switch (i) {
            case 1:
            case 2:
            case 4:
            case 5:
                objArr[0] = "org/jetbrains/kotlin/load/java/structure/impl/JavaTypeImpl";
                break;
            case 3:
                objArr[0] = "psiType";
                break;
            case 6:
                objArr[0] = "fqName";
                break;
            default:
                objArr[0] = "psiTypeSource";
                break;
        }
        if (i == 1) {
            objArr[1] = "getSourceFactory";
        } else if (i == 2) {
            objArr[1] = "getPsi";
        } else if (i == 4) {
            objArr[1] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
        } else if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/load/java/structure/impl/JavaTypeImpl";
        } else {
            objArr[1] = "getAnnotations";
        }
        switch (i) {
            case 1:
            case 2:
            case 4:
            case 5:
                break;
            case 3:
                objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
                break;
            case 6:
                objArr[2] = "findAnnotation";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 4 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: isDeprecatedInJavaDoc */
    public boolean getIsDeprecatedInJavaDoc() {
        return false;
    }

    public JavaTypeImpl(JavaElementTypeSource<Psi> javaElementTypeSource) {
        if (javaElementTypeSource == null) {
            $$$reportNull$$$0(0);
        }
        this.psiType = javaElementTypeSource;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.impl.source.JavaSourceFactoryOwner
    public JavaElementSourceFactory getSourceFactory() {
        JavaElementSourceFactory factory = this.psiType.getFactory();
        if (factory == null) {
            $$$reportNull$$$0(1);
        }
        return factory;
    }

    public Psi getPsi() {
        Psi psi = (Psi) this.psiType.getType();
        if (psi == null) {
            $$$reportNull$$$0(2);
        }
        return psi;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.impl.JavaAnnotationOwnerImpl
    public PsiAnnotationOwner getAnnotationOwnerPsi() {
        return getPsi();
    }

    public static JavaTypeImpl<?> create(JavaElementTypeSource<? extends PsiType> javaElementTypeSource) {
        return create(javaElementTypeSource.getType(), javaElementTypeSource);
    }

    @Deprecated
    public static JavaTypeImpl<?> create(PsiType psiType) {
        return create((JavaElementTypeSource<? extends PsiType>) new JavaFixedElementSourceFactory().createTypeSource(psiType));
    }

    public static JavaTypeImpl<?> create(PsiType psiType, final JavaElementTypeSource<? extends PsiType> javaElementTypeSource) {
        if (psiType == null) {
            $$$reportNull$$$0(3);
        }
        JavaTypeImpl<?> javaTypeImpl = (JavaTypeImpl) psiType.accept(new PsiTypeVisitor<JavaTypeImpl<?>>() { // from class: org.jetbrains.kotlin.load.java.structure.impl.JavaTypeImpl.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i == 1) {
                    objArr[0] = "primitiveType";
                } else if (i == 2) {
                    objArr[0] = "arrayType";
                } else if (i == 3) {
                    objArr[0] = "classType";
                } else if (i != 4) {
                    objArr[0] = ModuleXmlParser.TYPE;
                } else {
                    objArr[0] = "wildcardType";
                }
                objArr[1] = "org/jetbrains/kotlin/load/java/structure/impl/JavaTypeImpl$1";
                if (i == 1) {
                    objArr[2] = "visitPrimitiveType";
                } else if (i == 2) {
                    objArr[2] = "visitArrayType";
                } else if (i == 3) {
                    objArr[2] = "visitClassType";
                } else if (i != 4) {
                    objArr[2] = "visitType";
                } else {
                    objArr[2] = "visitWildcardType";
                }
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public JavaTypeImpl<?> visitType(PsiType psiType2) {
                if (psiType2 == null) {
                    $$$reportNull$$$0(0);
                }
                throw new UnsupportedOperationException("Unsupported PsiType: " + psiType2);
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public JavaTypeImpl<?> visitPrimitiveType(PsiPrimitiveType psiPrimitiveType) {
                if (psiPrimitiveType == null) {
                    $$$reportNull$$$0(1);
                }
                return new JavaPrimitiveTypeImpl(javaElementTypeSource);
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public JavaTypeImpl<?> visitArrayType(PsiArrayType psiArrayType) {
                if (psiArrayType == null) {
                    $$$reportNull$$$0(2);
                }
                return new JavaArrayTypeImpl(javaElementTypeSource);
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public JavaTypeImpl<?> visitClassType(PsiClassType psiClassType) {
                if (psiClassType == null) {
                    $$$reportNull$$$0(3);
                }
                return new JavaClassifierTypeImpl(javaElementTypeSource);
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public JavaTypeImpl<?> visitWildcardType(PsiWildcardType psiWildcardType) {
                if (psiWildcardType == null) {
                    $$$reportNull$$$0(4);
                }
                return new JavaWildcardTypeImpl(javaElementTypeSource);
            }
        });
        if (javaTypeImpl == null) {
            $$$reportNull$$$0(4);
        }
        return javaTypeImpl;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: getAnnotations */
    public Collection<JavaAnnotation> mo7252getAnnotations() {
        Collection<JavaAnnotation> annotations = JavaElementUtil.getAnnotations(this, getSourceFactory());
        if (annotations == null) {
            $$$reportNull$$$0(5);
        }
        return annotations;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.ListBasedJavaAnnotationOwner, org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: findAnnotation */
    public JavaAnnotation mo7251findAnnotation(FqName fqName) {
        if (fqName == null) {
            $$$reportNull$$$0(6);
        }
        return JavaElementUtil.findAnnotation(this, fqName, getSourceFactory());
    }

    public int hashCode() {
        return getPsi().hashCode();
    }

    public boolean equals(Object obj) {
        return (obj instanceof JavaTypeImpl) && getPsi().equals(((JavaTypeImpl) obj).getPsi());
    }

    public String toString() {
        return getClass().getSimpleName() + ": " + getPsi();
    }
}
