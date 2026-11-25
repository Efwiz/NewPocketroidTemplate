package org.eclipse.jdt.internal.compiler.apt.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.List;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.type.NoType;
import javax.lang.model.type.NullType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: loaded from: classes14.dex */
public class NoTypeImpl implements NoType, NullType {
    private static /* synthetic */ int[] $SWITCH_TABLE$javax$lang$model$type$TypeKind;
    private final TypeKind _kind;
    public static final NoType NO_TYPE_NONE = new NoTypeImpl(TypeKind.NONE);
    public static final NoType NO_TYPE_VOID = new NoTypeImpl(TypeKind.VOID);
    public static final NoType NO_TYPE_PACKAGE = new NoTypeImpl(TypeKind.PACKAGE);
    public static final NullType NULL_TYPE = new NoTypeImpl(TypeKind.NULL);

    public <A extends Annotation> A getAnnotation(Class<A> cls) {
        return null;
    }

    static /* synthetic */ int[] $SWITCH_TABLE$javax$lang$model$type$TypeKind() {
        int[] iArr = $SWITCH_TABLE$javax$lang$model$type$TypeKind;
        if (iArr != null) {
            return iArr;
        }
        int[] iArr2 = new int[TypeKind.values().length];
        try {
            iArr2[TypeKind.ARRAY.ordinal()] = 12;
        } catch (NoSuchFieldError unused) {
        }
        try {
            iArr2[TypeKind.BOOLEAN.ordinal()] = 1;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            iArr2[TypeKind.BYTE.ordinal()] = 2;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            iArr2[TypeKind.CHAR.ordinal()] = 6;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            iArr2[TypeKind.DECLARED.ordinal()] = 13;
        } catch (NoSuchFieldError unused5) {
        }
        try {
            iArr2[TypeKind.DOUBLE.ordinal()] = 8;
        } catch (NoSuchFieldError unused6) {
        }
        try {
            iArr2[TypeKind.ERROR.ordinal()] = 14;
        } catch (NoSuchFieldError unused7) {
        }
        try {
            iArr2[TypeKind.EXECUTABLE.ordinal()] = 18;
        } catch (NoSuchFieldError unused8) {
        }
        try {
            iArr2[TypeKind.FLOAT.ordinal()] = 7;
        } catch (NoSuchFieldError unused9) {
        }
        try {
            iArr2[TypeKind.INT.ordinal()] = 4;
        } catch (NoSuchFieldError unused10) {
        }
        try {
            iArr2[TypeKind.LONG.ordinal()] = 5;
        } catch (NoSuchFieldError unused11) {
        }
        try {
            iArr2[TypeKind.NONE.ordinal()] = 10;
        } catch (NoSuchFieldError unused12) {
        }
        try {
            iArr2[TypeKind.NULL.ordinal()] = 11;
        } catch (NoSuchFieldError unused13) {
        }
        try {
            iArr2[TypeKind.OTHER.ordinal()] = 19;
        } catch (NoSuchFieldError unused14) {
        }
        try {
            iArr2[TypeKind.PACKAGE.ordinal()] = 17;
        } catch (NoSuchFieldError unused15) {
        }
        try {
            iArr2[TypeKind.SHORT.ordinal()] = 3;
        } catch (NoSuchFieldError unused16) {
        }
        try {
            iArr2[TypeKind.TYPEVAR.ordinal()] = 15;
        } catch (NoSuchFieldError unused17) {
        }
        try {
            iArr2[TypeKind.UNION.ordinal()] = 20;
        } catch (NoSuchFieldError unused18) {
        }
        try {
            iArr2[TypeKind.VOID.ordinal()] = 9;
        } catch (NoSuchFieldError unused19) {
        }
        try {
            iArr2[TypeKind.WILDCARD.ordinal()] = 16;
        } catch (NoSuchFieldError unused20) {
        }
        $SWITCH_TABLE$javax$lang$model$type$TypeKind = iArr2;
        return iArr2;
    }

    private NoTypeImpl(TypeKind typeKind) {
        this._kind = typeKind;
    }

    public <R, P> R accept(TypeVisitor<R, P> typeVisitor, P p) {
        if ($SWITCH_TABLE$javax$lang$model$type$TypeKind()[getKind().ordinal()] == 11) {
            return (R) typeVisitor.visitNull(this, p);
        }
        return (R) typeVisitor.visitNoType(this, p);
    }

    public TypeKind getKind() {
        return this._kind;
    }

    public String toString() {
        int i = $SWITCH_TABLE$javax$lang$model$type$TypeKind()[this._kind.ordinal()];
        if (i == 9) {
            return PsiKeyword.VOID;
        }
        if (i == 11) {
            return "null";
        }
        if (i != 17) {
            return "none";
        }
        return "package";
    }

    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        return Factory.EMPTY_ANNOTATION_MIRRORS;
    }

    public <A extends Annotation> A[] getAnnotationsByType(Class<A> cls) {
        return (A[]) ((Annotation[]) Array.newInstance((Class<?>) cls, 0));
    }
}
