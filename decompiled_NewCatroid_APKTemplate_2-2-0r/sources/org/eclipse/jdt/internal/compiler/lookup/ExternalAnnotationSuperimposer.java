package org.eclipse.jdt.internal.compiler.lookup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.eclipse.jdt.internal.compiler.env.IBinaryAnnotation;
import org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker;
import org.eclipse.jdt.internal.compiler.util.Messages;

/* JADX INFO: loaded from: classes14.dex */
class ExternalAnnotationSuperimposer extends TypeBindingVisitor {
    private ITypeAnnotationWalker currentWalker;
    private LookupEnvironment environment;
    private boolean isReplacing;
    private TypeBinding typeReplacement;

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBindingVisitor
    public boolean visit(BaseTypeBinding baseTypeBinding) {
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBindingVisitor
    public boolean visit(IntersectionTypeBinding18 intersectionTypeBinding18) {
        return false;
    }

    public static void apply(SourceTypeBinding sourceTypeBinding, String str) {
        InputStream inputStream;
        ZipFile zipFile = null;
        try {
            try {
                File file = new File(str);
                if (file.exists()) {
                    String strValueOf = String.valueOf(sourceTypeBinding.constantPoolName());
                    String str2 = String.valueOf(strValueOf.replace('.', '/')) + ExternalAnnotationProvider.ANNOTATION_FILE_SUFFIX;
                    if (file.isDirectory()) {
                        inputStream = new FileInputStream(String.valueOf(str) + '/' + str2);
                    } else {
                        ZipFile zipFile2 = new ZipFile(str);
                        try {
                            ZipEntry entry = zipFile2.getEntry(str2);
                            if (entry != null) {
                                inputStream = zipFile2.getInputStream(entry);
                                zipFile = zipFile2;
                            } else {
                                try {
                                    zipFile2.close();
                                    return;
                                } catch (IOException unused) {
                                    return;
                                }
                            }
                        } catch (FileNotFoundException unused2) {
                            zipFile = zipFile2;
                            if (zipFile == null) {
                                return;
                            }
                        } catch (IOException e) {
                            e = e;
                            zipFile = zipFile2;
                            sourceTypeBinding.scope.problemReporter().abortDueToInternalError(Messages.bind(Messages.abort_externaAnnotationFile, (Object[]) new String[]{String.valueOf(sourceTypeBinding.readableName()), str, e.getMessage()}));
                            if (zipFile == null) {
                                return;
                            }
                        } catch (Throwable th) {
                            th = th;
                            zipFile = zipFile2;
                            if (zipFile != null) {
                                try {
                                    zipFile.close();
                                } catch (IOException unused3) {
                                }
                            }
                            throw th;
                        }
                    }
                    annotateType(sourceTypeBinding, new ExternalAnnotationProvider(inputStream, strValueOf), sourceTypeBinding.environment);
                }
                if (zipFile == null) {
                    return;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (FileNotFoundException unused4) {
        } catch (IOException e2) {
            e = e2;
        }
        try {
            zipFile.close();
        } catch (IOException unused5) {
        }
    }

    static void annotateType(SourceTypeBinding sourceTypeBinding, ExternalAnnotationProvider externalAnnotationProvider, LookupEnvironment lookupEnvironment) {
        ITypeAnnotationWalker iTypeAnnotationWalkerForTypeHeader = externalAnnotationProvider.forTypeHeader(lookupEnvironment);
        if (iTypeAnnotationWalkerForTypeHeader != null && iTypeAnnotationWalkerForTypeHeader != ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER) {
            ExternalAnnotationSuperimposer externalAnnotationSuperimposer = new ExternalAnnotationSuperimposer(lookupEnvironment);
            TypeVariableBinding[] typeVariableBindingArrTypeVariables = sourceTypeBinding.typeVariables();
            for (int i = 0; i < typeVariableBindingArrTypeVariables.length; i++) {
                if (externalAnnotationSuperimposer.go(iTypeAnnotationWalkerForTypeHeader.toTypeParameter(true, i))) {
                    typeVariableBindingArrTypeVariables[i] = (TypeVariableBinding) externalAnnotationSuperimposer.superimpose(typeVariableBindingArrTypeVariables[i], TypeVariableBinding.class);
                }
            }
        }
        sourceTypeBinding.externalAnnotationProvider = externalAnnotationProvider;
    }

    public static void annotateFieldBinding(FieldBinding fieldBinding, ExternalAnnotationProvider externalAnnotationProvider, LookupEnvironment lookupEnvironment) {
        char[] cArrGenericSignature = fieldBinding.genericSignature();
        if (cArrGenericSignature == null && fieldBinding.type != null) {
            cArrGenericSignature = fieldBinding.type.signature();
        }
        ITypeAnnotationWalker iTypeAnnotationWalkerForField = externalAnnotationProvider.forField(fieldBinding.f628name, cArrGenericSignature, lookupEnvironment);
        ExternalAnnotationSuperimposer externalAnnotationSuperimposer = new ExternalAnnotationSuperimposer(lookupEnvironment);
        if (externalAnnotationSuperimposer.go(iTypeAnnotationWalkerForField)) {
            fieldBinding.type = externalAnnotationSuperimposer.superimpose(fieldBinding.type, TypeBinding.class);
        }
    }

    public static void annotateMethodBinding(MethodBinding methodBinding, ExternalAnnotationProvider externalAnnotationProvider, LookupEnvironment lookupEnvironment) {
        char[] cArrGenericSignature = methodBinding.genericSignature();
        if (cArrGenericSignature == null) {
            cArrGenericSignature = methodBinding.signature();
        }
        ITypeAnnotationWalker iTypeAnnotationWalkerForMethod = externalAnnotationProvider.forMethod(methodBinding.selector, cArrGenericSignature, lookupEnvironment);
        if (iTypeAnnotationWalkerForMethod == null || iTypeAnnotationWalkerForMethod == ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER) {
            return;
        }
        ExternalAnnotationSuperimposer externalAnnotationSuperimposer = new ExternalAnnotationSuperimposer(lookupEnvironment);
        TypeVariableBinding[] typeVariableBindingArr = methodBinding.typeVariables;
        for (short s = 0; s < typeVariableBindingArr.length; s = (short) (s + 1)) {
            if (externalAnnotationSuperimposer.go(iTypeAnnotationWalkerForMethod.toTypeParameter(false, s))) {
                typeVariableBindingArr[s] = (TypeVariableBinding) externalAnnotationSuperimposer.superimpose(typeVariableBindingArr[s], TypeVariableBinding.class);
            }
        }
        if (!methodBinding.isConstructor() && externalAnnotationSuperimposer.go(iTypeAnnotationWalkerForMethod.toMethodReturn())) {
            methodBinding.returnType = externalAnnotationSuperimposer.superimpose(methodBinding.returnType, TypeBinding.class);
        }
        TypeBinding[] typeBindingArr = methodBinding.parameters;
        for (short s2 = 0; s2 < typeBindingArr.length; s2 = (short) (s2 + 1)) {
            if (externalAnnotationSuperimposer.go(iTypeAnnotationWalkerForMethod.toMethodParameter(s2))) {
                typeBindingArr[s2] = externalAnnotationSuperimposer.superimpose(typeBindingArr[s2], TypeBinding.class);
            }
        }
    }

    ExternalAnnotationSuperimposer(LookupEnvironment lookupEnvironment) {
        this.environment = lookupEnvironment;
    }

    private ExternalAnnotationSuperimposer(TypeBinding typeBinding, boolean z, ITypeAnnotationWalker iTypeAnnotationWalker) {
        this.typeReplacement = typeBinding;
        this.isReplacing = z;
        this.currentWalker = iTypeAnnotationWalker;
    }

    private ExternalAnnotationSuperimposer snapshot() {
        ExternalAnnotationSuperimposer externalAnnotationSuperimposer = new ExternalAnnotationSuperimposer(this.typeReplacement, this.isReplacing, this.currentWalker);
        this.typeReplacement = null;
        this.isReplacing = false;
        return externalAnnotationSuperimposer;
    }

    private void restore(ExternalAnnotationSuperimposer externalAnnotationSuperimposer) {
        this.isReplacing = externalAnnotationSuperimposer.isReplacing;
        this.currentWalker = externalAnnotationSuperimposer.currentWalker;
    }

    boolean go(ITypeAnnotationWalker iTypeAnnotationWalker) {
        reset();
        this.typeReplacement = null;
        this.isReplacing = false;
        this.currentWalker = iTypeAnnotationWalker;
        return iTypeAnnotationWalker != ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER;
    }

    <T extends TypeBinding> T superimpose(T t, Class<? extends T> cls) {
        TypeBindingVisitor.visit(this, t);
        return cls.isInstance(this.typeReplacement) ? cls.cast(this.typeReplacement) : t;
    }

    private TypeBinding goAndSuperimpose(ITypeAnnotationWalker iTypeAnnotationWalker, TypeBinding typeBinding) {
        if (iTypeAnnotationWalker == ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER) {
            return typeBinding;
        }
        this.currentWalker = iTypeAnnotationWalker;
        TypeBindingVisitor.visit(this, typeBinding);
        TypeBinding typeBinding2 = this.typeReplacement;
        if (typeBinding2 == null) {
            return typeBinding;
        }
        this.isReplacing = true;
        this.typeReplacement = null;
        return typeBinding2;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBindingVisitor
    public boolean visit(ArrayBinding arrayBinding) {
        ExternalAnnotationSuperimposer externalAnnotationSuperimposerSnapshot = snapshot();
        try {
            int i = arrayBinding.dimensions;
            AnnotationBinding[][] annotationBindingArr = new AnnotationBinding[i][];
            ITypeAnnotationWalker nextArrayDimension = this.currentWalker;
            for (int i2 = 0; i2 < i; i2++) {
                IBinaryAnnotation[] annotationsAtCursor = nextArrayDimension.getAnnotationsAtCursor(arrayBinding.f626id);
                if (annotationsAtCursor != ITypeAnnotationWalker.NO_ANNOTATIONS) {
                    annotationBindingArr[i2] = BinaryTypeBinding.createAnnotations(annotationsAtCursor, this.environment, null);
                    this.isReplacing = true;
                } else {
                    annotationBindingArr[i2] = Binding.NO_ANNOTATIONS;
                }
                nextArrayDimension = nextArrayDimension.toNextArrayDimension();
            }
            TypeBinding typeBindingGoAndSuperimpose = goAndSuperimpose(nextArrayDimension, arrayBinding.leafComponentType());
            if (this.isReplacing) {
                this.typeReplacement = this.environment.createArrayType(typeBindingGoAndSuperimpose, i, AnnotatableTypeSystem.flattenedAnnotations(annotationBindingArr));
            }
            return false;
        } finally {
            restore(externalAnnotationSuperimposerSnapshot);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBindingVisitor
    public boolean visit(ParameterizedTypeBinding parameterizedTypeBinding) {
        ExternalAnnotationSuperimposer externalAnnotationSuperimposerSnapshot = snapshot();
        try {
            IBinaryAnnotation[] annotationsAtCursor = this.currentWalker.getAnnotationsAtCursor(parameterizedTypeBinding.f626id);
            AnnotationBinding[] annotationBindingArrCreateAnnotations = Binding.NO_ANNOTATIONS;
            if (annotationsAtCursor != ITypeAnnotationWalker.NO_ANNOTATIONS) {
                annotationBindingArrCreateAnnotations = BinaryTypeBinding.createAnnotations(annotationsAtCursor, this.environment, null);
                this.isReplacing = true;
            }
            TypeBinding[] typeBindingArrTypeArguments = parameterizedTypeBinding.typeArguments();
            TypeBinding[] typeBindingArr = new TypeBinding[typeBindingArrTypeArguments.length];
            for (int i = 0; i < typeBindingArrTypeArguments.length; i++) {
                typeBindingArr[i] = goAndSuperimpose(externalAnnotationSuperimposerSnapshot.currentWalker.toTypeArgument(i), typeBindingArrTypeArguments[i]);
            }
            if (this.isReplacing) {
                this.typeReplacement = this.environment.createParameterizedType(parameterizedTypeBinding.genericType(), typeBindingArr, parameterizedTypeBinding.enclosingType(), annotationBindingArrCreateAnnotations);
            }
            return false;
        } finally {
            restore(externalAnnotationSuperimposerSnapshot);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBindingVisitor
    public boolean visit(RawTypeBinding rawTypeBinding) {
        return visit((ReferenceBinding) rawTypeBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBindingVisitor
    public boolean visit(ReferenceBinding referenceBinding) {
        IBinaryAnnotation[] annotationsAtCursor = this.currentWalker.getAnnotationsAtCursor(referenceBinding.f626id);
        if (annotationsAtCursor == ITypeAnnotationWalker.NO_ANNOTATIONS) {
            return false;
        }
        LookupEnvironment lookupEnvironment = this.environment;
        this.typeReplacement = lookupEnvironment.createAnnotatedType(referenceBinding, BinaryTypeBinding.createAnnotations(annotationsAtCursor, lookupEnvironment, null));
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBindingVisitor
    public boolean visit(TypeVariableBinding typeVariableBinding) {
        return visit((ReferenceBinding) typeVariableBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBindingVisitor
    public boolean visit(WildcardBinding wildcardBinding) {
        TypeBinding typeBindingGoAndSuperimpose = wildcardBinding.bound;
        ExternalAnnotationSuperimposer externalAnnotationSuperimposerSnapshot = snapshot();
        if (typeBindingGoAndSuperimpose != null) {
            try {
                typeBindingGoAndSuperimpose = goAndSuperimpose(externalAnnotationSuperimposerSnapshot.currentWalker.toWildcardBound(), typeBindingGoAndSuperimpose);
            } finally {
                restore(externalAnnotationSuperimposerSnapshot);
            }
        }
        TypeBinding typeBinding = typeBindingGoAndSuperimpose;
        IBinaryAnnotation[] annotationsAtCursor = externalAnnotationSuperimposerSnapshot.currentWalker.getAnnotationsAtCursor(-1);
        if (this.isReplacing || annotationsAtCursor != ITypeAnnotationWalker.NO_ANNOTATIONS) {
            TypeBinding[] typeBindingArr = wildcardBinding.otherBounds;
            if (annotationsAtCursor != ITypeAnnotationWalker.NO_ANNOTATIONS) {
                this.typeReplacement = this.environment.createWildcard(wildcardBinding.genericType, wildcardBinding.rank, typeBinding, typeBindingArr, wildcardBinding.boundKind, BinaryTypeBinding.createAnnotations(annotationsAtCursor, this.environment, null));
            } else {
                this.typeReplacement = this.environment.createWildcard(wildcardBinding.genericType, wildcardBinding.rank, typeBinding, typeBindingArr, wildcardBinding.boundKind);
            }
        }
        return false;
    }
}
