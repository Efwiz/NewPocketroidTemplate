package org.eclipse.jdt.internal.compiler.lookup;

/* JADX INFO: loaded from: classes14.dex */
public class UnresolvedAnnotationBinding extends AnnotationBinding {
    private LookupEnvironment env;
    private boolean typeUnresolved;

    UnresolvedAnnotationBinding(ReferenceBinding referenceBinding, ElementValuePair[] elementValuePairArr, LookupEnvironment lookupEnvironment) {
        super(referenceBinding, elementValuePairArr);
        this.typeUnresolved = true;
        this.env = lookupEnvironment;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.AnnotationBinding
    public void resolve() {
        if (this.typeUnresolved) {
            boolean z = this.env.mayTolerateMissingType;
            this.env.mayTolerateMissingType = true;
            try {
                this.type = (ReferenceBinding) BinaryTypeBinding.resolveType(this.type, this.env, false);
                this.env.mayTolerateMissingType = z;
                this.typeUnresolved = false;
            } catch (Throwable th) {
                this.env.mayTolerateMissingType = z;
                throw th;
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.AnnotationBinding
    public ReferenceBinding getAnnotationType() {
        resolve();
        return this.type;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.AnnotationBinding
    public ElementValuePair[] getElementValuePairs() {
        if (this.env != null) {
            if (this.typeUnresolved) {
                resolve();
            }
            int length = this.pairs.length;
            while (true) {
                length--;
                if (length < 0) {
                    break;
                }
                ElementValuePair elementValuePair = this.pairs[length];
                MethodBinding[] methods = this.type.getMethods(elementValuePair.getName());
                if (methods != null && methods.length == 1) {
                    elementValuePair.setMethodBinding(methods[0]);
                }
                Object value = elementValuePair.getValue();
                if (value instanceof UnresolvedReferenceBinding) {
                    elementValuePair.setValue(((UnresolvedReferenceBinding) value).resolve(this.env, false));
                }
            }
            this.env = null;
        }
        return this.pairs;
    }
}
