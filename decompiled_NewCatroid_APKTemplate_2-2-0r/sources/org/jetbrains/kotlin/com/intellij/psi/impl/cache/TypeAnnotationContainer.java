package org.jetbrains.kotlin.com.intellij.psi.impl.cache;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.NotNullLazyValue;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiCompiledElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiEllipsisType;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameValuePair;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.TypeAnnotationProvider;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsAnnotationParameterListImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsJavaCodeReferenceElementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiAnnotationStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public class TypeAnnotationContainer {
    public static final TypeAnnotationContainer EMPTY = new TypeAnnotationContainer(Collections.emptyList());
    private final List<TypeAnnotationEntry> myList;

    /* JADX WARN: Code duplicated, block: B:20:0x002b  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 5 || i == 6) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 4 || i == 5 || i == 6) ? 3 : 2];
        if (i == 4) {
            objArr[0] = "dataStream";
        } else if (i == 5) {
            objArr[0] = "container";
        } else if (i != 6) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/cache/TypeAnnotationContainer";
        } else {
            objArr[0] = "dataStream";
        }
        switch (i) {
            case 1:
                objArr[1] = "forEnclosingClass";
                break;
            case 2:
                objArr[1] = "forBound";
                break;
            case 3:
                objArr[1] = "forTypeArgument";
                break;
            case 4:
            case 5:
            case 6:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/cache/TypeAnnotationContainer";
                break;
            default:
                objArr[1] = "forArrayElement";
                break;
        }
        if (i == 4 || i == 5) {
            objArr[2] = "writeTypeAnnotations";
        } else if (i == 6) {
            objArr[2] = "readTypeAnnotations";
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 5 && i != 6) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    private TypeAnnotationContainer(List<TypeAnnotationEntry> list) {
        this.myList = list;
    }

    public TypeAnnotationContainer forArrayElement() {
        if (isEmpty()) {
            return this;
        }
        List listMapNotNull = ContainerUtil.mapNotNull(this.myList, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.cache.TypeAnnotationContainer$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return ((TypeAnnotationContainer.TypeAnnotationEntry) obj).forPathElement(0);
            }
        });
        return listMapNotNull.isEmpty() ? EMPTY : new TypeAnnotationContainer(listMapNotNull);
    }

    public TypeAnnotationContainer forEnclosingClass() {
        if (isEmpty()) {
            return this;
        }
        List listMapNotNull = ContainerUtil.mapNotNull(this.myList, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.cache.TypeAnnotationContainer$$ExternalSyntheticLambda3
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return ((TypeAnnotationContainer.TypeAnnotationEntry) obj).forPathElement(1);
            }
        });
        return listMapNotNull.isEmpty() ? EMPTY : new TypeAnnotationContainer(listMapNotNull);
    }

    public TypeAnnotationContainer forBound() {
        if (isEmpty()) {
            return this;
        }
        List listMapNotNull = ContainerUtil.mapNotNull(this.myList, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.cache.TypeAnnotationContainer$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return ((TypeAnnotationContainer.TypeAnnotationEntry) obj).forPathElement(2);
            }
        });
        return listMapNotNull.isEmpty() ? EMPTY : new TypeAnnotationContainer(listMapNotNull);
    }

    public TypeAnnotationContainer forTypeArgument(final int i) {
        if (isEmpty()) {
            return this;
        }
        List listMapNotNull = ContainerUtil.mapNotNull(this.myList, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.cache.TypeAnnotationContainer$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return ((TypeAnnotationContainer.TypeAnnotationEntry) obj).forTypeArgument(i);
            }
        });
        return listMapNotNull.isEmpty() ? EMPTY : new TypeAnnotationContainer(listMapNotNull);
    }

    public boolean isEmpty() {
        return this.myList.isEmpty();
    }

    public TypeAnnotationProvider getProvider(final PsiElement psiElement) {
        return isEmpty() ? TypeAnnotationProvider.EMPTY : new TypeAnnotationProvider() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.cache.TypeAnnotationContainer.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/impl/cache/TypeAnnotationContainer$1", "getAnnotations"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.TypeAnnotationProvider
            public PsiAnnotation[] getAnnotations() {
                ArrayList arrayList = new ArrayList();
                for (TypeAnnotationEntry typeAnnotationEntry : TypeAnnotationContainer.this.myList) {
                    if (typeAnnotationEntry.myPath.length == 0) {
                        PsiElement psiElement2 = psiElement;
                        arrayList.add(psiElement2 instanceof PsiCompiledElement ? new ClsTypeAnnotationImpl(psiElement, typeAnnotationEntry.myText) : JavaPsiFacade.getElementFactory(psiElement2.getProject()).createAnnotationFromText(typeAnnotationEntry.myText, psiElement));
                    }
                }
                PsiAnnotation[] psiAnnotationArr = (PsiAnnotation[]) arrayList.toArray(PsiAnnotation.EMPTY_ARRAY);
                if (psiAnnotationArr == null) {
                    $$$reportNull$$$0(0);
                }
                return psiAnnotationArr;
            }
        };
    }

    public void createAnnotationStubs(StubElement<?> stubElement) {
        for (TypeAnnotationEntry typeAnnotationEntry : this.myList) {
            if (typeAnnotationEntry.myPath.length == 0) {
                new PsiAnnotationStubImpl(stubElement, typeAnnotationEntry.myText);
            }
        }
    }

    public PsiType applyTo(PsiType psiType, PsiElement psiElement) {
        PsiType componentType;
        PsiType psiTypeApplyTo;
        if (isEmpty()) {
            return psiType;
        }
        if ((psiType instanceof PsiArrayType) && componentType != (psiTypeApplyTo = forArrayElement().applyTo((componentType = ((PsiArrayType) psiType).getComponentType()), psiElement))) {
            psiType = psiType instanceof PsiEllipsisType ? new PsiEllipsisType(psiTypeApplyTo) : psiTypeApplyTo.createArrayType();
        }
        return psiType.annotate(getProvider(psiElement));
    }

    public static void writeTypeAnnotations(StubOutputStream stubOutputStream, TypeAnnotationContainer typeAnnotationContainer) throws IOException {
        if (stubOutputStream == null) {
            $$$reportNull$$$0(4);
        }
        if (typeAnnotationContainer == null) {
            $$$reportNull$$$0(5);
        }
        stubOutputStream.writeShort(typeAnnotationContainer.myList.size());
        for (TypeAnnotationEntry typeAnnotationEntry : typeAnnotationContainer.myList) {
            stubOutputStream.writeShort(typeAnnotationEntry.myPath.length);
            stubOutputStream.write(typeAnnotationEntry.myPath);
            stubOutputStream.writeUTFFast(typeAnnotationEntry.myText);
        }
    }

    public static TypeAnnotationContainer readTypeAnnotations(StubInputStream stubInputStream) throws IOException {
        if (stubInputStream == null) {
            $$$reportNull$$$0(6);
        }
        int i = stubInputStream.readShort();
        TypeAnnotationEntry[] typeAnnotationEntryArr = new TypeAnnotationEntry[i];
        for (int i2 = 0; i2 < i; i2++) {
            byte[] bArr = new byte[stubInputStream.readShort()];
            stubInputStream.readFully(bArr);
            typeAnnotationEntryArr[i2] = new TypeAnnotationEntry(bArr, stubInputStream.readUTFFast());
        }
        return new TypeAnnotationContainer(Arrays.asList(typeAnnotationEntryArr));
    }

    public String toString() {
        return StringUtil.join((Iterable<?>) this.myList, "\n");
    }

    public static class Collector {
        public static final byte ARRAY_ELEMENT = 0;
        public static final byte ENCLOSING_CLASS = 1;
        public static final byte TYPE_ARGUMENT = 3;
        public static final byte WILDCARD_BOUND = 2;
        private final ArrayList<TypeAnnotationEntry> myList;
        protected final TypeInfo myTypeInfo;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "text";
            } else if (i != 2) {
                objArr[0] = CompilerOptions.INFO;
            } else {
                objArr[0] = "path";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/cache/TypeAnnotationContainer$Collector";
            if (i == 1 || i == 2) {
                objArr[2] = ProductAction.ACTION_ADD;
            } else {
                objArr[2] = "<init>";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        public Collector(TypeInfo typeInfo) {
            if (typeInfo == null) {
                $$$reportNull$$$0(0);
            }
            this.myList = new ArrayList<>();
            this.myTypeInfo = typeInfo;
        }

        public void add(byte[] bArr, String str) {
            if (str == null) {
                $$$reportNull$$$0(1);
            }
            if (bArr == null) {
                $$$reportNull$$$0(2);
            }
            this.myList.add(new TypeAnnotationEntry(bArr, str));
        }

        public void install() {
            if (this.myList.isEmpty()) {
                this.myTypeInfo.setTypeAnnotations(TypeAnnotationContainer.EMPTY);
            } else {
                this.myList.trimToSize();
                this.myTypeInfo.setTypeAnnotations(new TypeAnnotationContainer(this.myList));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class TypeAnnotationEntry {
        final byte[] myPath;
        final String myText;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i != 1) {
                objArr[0] = "text";
            } else {
                objArr[0] = "path";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/cache/TypeAnnotationContainer$TypeAnnotationEntry";
            objArr[2] = "<init>";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        private TypeAnnotationEntry(byte[] bArr, String str) {
            if (str == null) {
                $$$reportNull$$$0(0);
            }
            if (bArr == null) {
                $$$reportNull$$$0(1);
            }
            this.myPath = bArr;
            this.myText = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public TypeAnnotationEntry forPathElement(int i) {
            byte[] bArr = this.myPath;
            if (bArr.length <= 0 || bArr[0] != i) {
                return null;
            }
            return new TypeAnnotationEntry(Arrays.copyOfRange(bArr, 1, bArr.length), this.myText);
        }

        public TypeAnnotationEntry forTypeArgument(int i) {
            byte[] bArr = this.myPath;
            if (bArr.length > 1 && bArr[0] == 3 && bArr[1] == i) {
                return new TypeAnnotationEntry(Arrays.copyOfRange(bArr, 2, bArr.length), this.myText);
            }
            return null;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            int i = 0;
            while (true) {
                byte[] bArr = this.myPath;
                if (i < bArr.length) {
                    byte b = bArr[i];
                    if (b == 0) {
                        sb.append('[');
                    } else if (b == 1) {
                        sb.append('.');
                    } else if (b == 2) {
                        sb.append('*');
                    } else if (b == 3) {
                        i++;
                        sb.append((int) bArr[i]).append(';');
                    }
                    i++;
                } else {
                    return ((Object) sb) + "->" + this.myText;
                }
            }
        }
    }

    static class ClsTypeAnnotationImpl extends ClsElementImpl implements PsiAnnotation {
        private final PsiElement myParent;
        private final String myText;
        private final NotNullLazyValue<ClsJavaCodeReferenceElementImpl> myReferenceElement = NotNullLazyValue.atomicLazy(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.cache.TypeAnnotationContainer$ClsTypeAnnotationImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.m6578xd3cfbaa9();
            }
        });
        private final NotNullLazyValue<ClsAnnotationParameterListImpl> myParameterList = NotNullLazyValue.atomicLazy(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.cache.TypeAnnotationContainer$ClsTypeAnnotationImpl$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.m6579xb4518288();
            }
        });

        /* JADX WARN: Code duplicated, block: B:30:0x0049  */
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 2 || i == 3 || i == 5) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[(i == 2 || i == 3 || i == 5) ? 3 : 2];
            if (i == 2) {
                objArr[0] = "buffer";
            } else if (i == 3) {
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
            } else if (i != 5) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/cache/TypeAnnotationContainer$ClsTypeAnnotationImpl";
            } else {
                objArr[0] = "visitor";
            }
            if (i == 1) {
                objArr[1] = "getNameReferenceElement";
            } else if (i == 2 || i == 3) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/cache/TypeAnnotationContainer$ClsTypeAnnotationImpl";
            } else if (i == 4) {
                objArr[1] = "getChildren";
            } else if (i != 5) {
                objArr[1] = "getParameterList";
            } else {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/cache/TypeAnnotationContainer$ClsTypeAnnotationImpl";
            }
            if (i == 2) {
                objArr[2] = "appendMirrorText";
            } else if (i == 3) {
                objArr[2] = "setMirror";
            } else if (i == 5) {
                objArr[2] = "accept";
            }
            String str2 = String.format(str, objArr);
            if (i != 2 && i != 3 && i != 5) {
                throw new IllegalStateException(str2);
            }
            throw new IllegalArgumentException(str2);
        }

        ClsTypeAnnotationImpl(PsiElement psiElement, String str) {
            this.myParent = psiElement;
            this.myText = str;
        }

        /* JADX INFO: renamed from: lambda$new$0$org-jetbrains-kotlin-com-intellij-psi-impl-cache-TypeAnnotationContainer$ClsTypeAnnotationImpl, reason: not valid java name */
        /* synthetic */ ClsJavaCodeReferenceElementImpl m6578xd3cfbaa9() {
            int iIndexOf = this.myText.indexOf(40);
            return new ClsJavaCodeReferenceElementImpl(this, iIndexOf > 0 ? this.myText.substring(1, iIndexOf) : this.myText.substring(1));
        }

        /* JADX INFO: renamed from: lambda$new$1$org-jetbrains-kotlin-com-intellij-psi-impl-cache-TypeAnnotationContainer$ClsTypeAnnotationImpl, reason: not valid java name */
        /* synthetic */ ClsAnnotationParameterListImpl m6579xb4518288() {
            PsiNameValuePair[] attributes;
            if (this.myText.indexOf(40) > 0) {
                attributes = JavaPsiFacade.getElementFactory(getProject()).createAnnotationFromText(this.myText, this.myParent).getParameterList().getAttributes();
            } else {
                attributes = PsiNameValuePair.EMPTY_ARRAY;
            }
            return new ClsAnnotationParameterListImpl(this, attributes);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
        public PsiAnnotationParameterList getParameterList() {
            ClsAnnotationParameterListImpl value = this.myParameterList.getValue();
            if (value == null) {
                $$$reportNull$$$0(0);
            }
            return value;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
        public String getQualifiedName() {
            return getNameReferenceElement().getCanonicalText();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
        public PsiJavaCodeReferenceElement getNameReferenceElement() {
            ClsJavaCodeReferenceElementImpl value = this.myReferenceElement.getValue();
            if (value == null) {
                $$$reportNull$$$0(1);
            }
            return value;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
        public PsiAnnotationMemberValue findAttributeValue(String str) {
            return PsiImplUtil.findAttributeValue(this, str);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
        public PsiAnnotationMemberValue findDeclaredAttributeValue(String str) {
            return PsiImplUtil.findDeclaredAttributeValue(this, str);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
        /* JADX INFO: renamed from: setDeclaredAttributeValue */
        public <T extends PsiAnnotationMemberValue> T mo6097setDeclaredAttributeValue(String str, T t) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
        public PsiAnnotationOwner getOwner() {
            return (PsiAnnotationOwner) ObjectUtils.tryCast(this.myParent, PsiAnnotationOwner.class);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
        public void appendMirrorText(int i, StringBuilder sb) {
            if (sb == null) {
                $$$reportNull$$$0(2);
            }
            sb.append(this.myText);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public String getText() {
            return this.myText;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
        public void setMirror(TreeElement treeElement) throws ClsElementImpl.InvalidMirrorException {
            if (treeElement == null) {
                $$$reportNull$$$0(3);
            }
            setMirrorCheckingType(treeElement, null);
            PsiAnnotation psiAnnotation = (PsiAnnotation) SourceTreeToPsiMap.treeToPsiNotNull(treeElement);
            setMirror(getNameReferenceElement(), psiAnnotation.getNameReferenceElement());
            setMirror(getParameterList(), psiAnnotation.getParameterList());
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement[] getChildren() {
            return new PsiElement[]{this.myReferenceElement.getValue(), getParameterList()};
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement getParent() {
            return this.myParent;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public void accept(PsiElementVisitor psiElementVisitor) {
            if (psiElementVisitor == null) {
                $$$reportNull$$$0(5);
            }
            if (psiElementVisitor instanceof JavaElementVisitor) {
                ((JavaElementVisitor) psiElementVisitor).visitAnnotation(this);
            } else {
                psiElementVisitor.visitElement(this);
            }
        }
    }
}
