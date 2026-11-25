package org.jf.dexlib2.dexbacked;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import org.jf.dexlib2.base.reference.BaseTypeReference;
import org.jf.dexlib2.dexbacked.model.DexTypeString;
import org.jf.dexlib2.dexbacked.util.AnnotationsDirectory;
import org.jf.dexlib2.dexbacked.util.EncodedArrayItemIterator;
import org.jf.dexlib2.dexbacked.util.VariableSizeListIterator;
import org.jf.dexlib2.dexbacked.util.VariableSizeLookaheadIterator;
import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.iface.reference.FieldReference;
import org.jf.dexlib2.iface.reference.MethodReference;
import org.jf.dexlib2.immutable.reference.ImmutableFieldReference;
import org.jf.dexlib2.immutable.reference.ImmutableMethodReference;
import org.jf.util.collection.ComputingList;
import org.jf.util.collection.EmptyList;
import org.jf.util.collection.EmptySet;
import org.jf.util.collection.Iterables;

/* JADX INFO: loaded from: classes6.dex */
public class DexBackedClassDef extends BaseTypeReference implements ClassDef {
    static final int NO_HIDDEN_API_RESTRICTIONS = 7;
    private AnnotationsDirectory annotationsDirectory;
    final int classDefOffset;
    public final DexBackedDexFile dexFile;
    private DexTypeString dexTypeString;
    private final int directMethodCount;
    private final HiddenApiRestrictionsReader hiddenApiRestrictionsReader;
    private final int instanceFieldCount;
    private final DexBackedSourceFileDef sourceFileDef;
    private final int staticFieldCount;
    private final int staticFieldsOffset;
    private final DexBackedSuperDef superDef;
    private final int virtualMethodCount;
    private int instanceFieldsOffset = 0;
    private int directMethodsOffset = 0;
    private int virtualMethodsOffset = 0;

    public DexBackedClassDef(DexBackedDexFile dexBackedDexFile, int i, int i2) {
        this.dexFile = dexBackedDexFile;
        this.classDefOffset = i;
        int smallUint = dexBackedDexFile.getBuffer().readSmallUint(i + 24);
        if (smallUint == 0) {
            this.staticFieldsOffset = -1;
            this.staticFieldCount = 0;
            this.instanceFieldCount = 0;
            this.directMethodCount = 0;
            this.virtualMethodCount = 0;
        } else {
            DexReader<? extends DexBuffer> dexReader = dexBackedDexFile.getDataBuffer().readerAt(smallUint);
            this.staticFieldCount = dexReader.readSmallUleb128();
            this.instanceFieldCount = dexReader.readSmallUleb128();
            this.directMethodCount = dexReader.readSmallUleb128();
            this.virtualMethodCount = dexReader.readSmallUleb128();
            this.staticFieldsOffset = dexReader.getOffset();
        }
        if (i2 != 0) {
            this.hiddenApiRestrictionsReader = new HiddenApiRestrictionsReader(i2);
        } else {
            this.hiddenApiRestrictionsReader = null;
        }
        this.superDef = new DexBackedSuperDef(this);
        this.sourceFileDef = new DexBackedSourceFileDef(this);
    }

    @Override // org.jf.dexlib2.iface.reference.TypeReference, org.jf.dexlib2.iface.ClassDef
    public String getType() {
        return getTypeString().getValue();
    }

    public DexTypeString getTypeString() {
        if (this.dexTypeString == null) {
            this.dexTypeString = this.dexFile.getTypeStringSection().get(this.dexFile.getBuffer().readSmallUint(this.classDefOffset));
        }
        return this.dexTypeString;
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public String getSuperclass() {
        return getSuperDef().getType();
    }

    public DexBackedSuperDef getSuperDef() {
        return this.superDef;
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public int getAccessFlags() {
        return this.dexFile.getBuffer().readSmallUint(this.classDefOffset + 4);
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public String getSourceFile() {
        return getSourceFileDef().getType();
    }

    public DexBackedSourceFileDef getSourceFileDef() {
        return this.sourceFileDef;
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public List<String> getInterfaces() {
        List<DexBackedInterfaceDef> interfaceDef = getInterfaceDef();
        if (interfaceDef.size() == 0) {
            return EmptyList.of();
        }
        return new ComputingList(interfaceDef, new Function<DexBackedInterfaceDef, String>() { // from class: org.jf.dexlib2.dexbacked.DexBackedClassDef.1
            @Override // java.util.function.Function
            public String apply(DexBackedInterfaceDef dexBackedInterfaceDef) {
                return dexBackedInterfaceDef.getType();
            }
        });
    }

    public List<DexBackedInterfaceDef> getInterfaceDef() {
        int smallUint = this.dexFile.getBuffer().readSmallUint(this.classDefOffset + 12);
        if (smallUint > 0) {
            final int smallUint2 = this.dexFile.getDataBuffer().readSmallUint(smallUint);
            return new AbstractList<DexBackedInterfaceDef>() { // from class: org.jf.dexlib2.dexbacked.DexBackedClassDef.2
                @Override // java.util.AbstractList, java.util.List
                public DexBackedInterfaceDef get(int i) {
                    return new DexBackedInterfaceDef(DexBackedClassDef.this, i);
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
                public int size() {
                    return smallUint2;
                }
            };
        }
        return EmptyList.of();
    }

    @Override // org.jf.dexlib2.iface.ClassDef, org.jf.dexlib2.iface.Annotatable
    public Set<? extends DexBackedAnnotation> getAnnotations() {
        return getAnnotationsDirectory().getClassAnnotations();
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public Iterable<? extends DexBackedField> getStaticFields() {
        return getStaticFields(true);
    }

    public Iterable<? extends DexBackedField> getStaticFields(final boolean z) {
        if (this.staticFieldCount > 0) {
            DexReader<? extends DexBuffer> dexReader = this.dexFile.getDataBuffer().readerAt(this.staticFieldsOffset);
            final AnnotationsDirectory annotationsDirectory = getAnnotationsDirectory();
            final int smallUint = this.dexFile.getBuffer().readSmallUint(this.classDefOffset + 28);
            final int offset = dexReader.getOffset();
            HiddenApiRestrictionsReader hiddenApiRestrictionsReader = this.hiddenApiRestrictionsReader;
            final VariableSizeListIterator restrictionsForStaticFields = hiddenApiRestrictionsReader == null ? null : hiddenApiRestrictionsReader.getRestrictionsForStaticFields();
            return new Iterable<DexBackedField>() { // from class: org.jf.dexlib2.dexbacked.DexBackedClassDef.3
                @Override // java.lang.Iterable
                public Iterator<DexBackedField> iterator() {
                    final AnnotationsDirectory.AnnotationIterator fieldAnnotationIterator = annotationsDirectory.getFieldAnnotationIterator();
                    final EncodedArrayItemIterator encodedArrayItemIteratorNewOrEmpty = EncodedArrayItemIterator.newOrEmpty(DexBackedClassDef.this.dexFile, smallUint);
                    return new VariableSizeLookaheadIterator<DexBackedField>(DexBackedClassDef.this.dexFile.getDataBuffer(), offset) { // from class: org.jf.dexlib2.dexbacked.DexBackedClassDef.3.1
                        private int count;
                        private FieldReference previousField;
                        private int previousIndex;

                        @Override // org.jf.dexlib2.dexbacked.util.VariableSizeLookaheadIterator
                        protected /* bridge */ /* synthetic */ DexBackedField readNextItem(DexReader dexReader2) {
                            return readNextItem2((DexReader<?>) dexReader2);
                        }

                        @Override // org.jf.dexlib2.dexbacked.util.VariableSizeLookaheadIterator
                        /* JADX INFO: renamed from: readNextItem, reason: avoid collision after fix types in other method */
                        protected DexBackedField readNextItem2(DexReader<?> dexReader2) {
                            DexBackedField dexBackedField;
                            FieldReference fieldReference;
                            ImmutableFieldReference immutableFieldReferenceOf;
                            do {
                                int i = this.count + 1;
                                this.count = i;
                                if (i > DexBackedClassDef.this.staticFieldCount) {
                                    DexBackedClassDef.this.instanceFieldsOffset = dexReader2.getOffset();
                                    return endOfData();
                                }
                                dexBackedField = new DexBackedField(DexBackedClassDef.this.dexFile, dexReader2, DexBackedClassDef.this, this.previousIndex, encodedArrayItemIteratorNewOrEmpty, fieldAnnotationIterator, restrictionsForStaticFields != null ? ((Integer) restrictionsForStaticFields.next()).intValue() : 7);
                                fieldReference = this.previousField;
                                immutableFieldReferenceOf = ImmutableFieldReference.of(dexBackedField);
                                this.previousField = immutableFieldReferenceOf;
                                this.previousIndex = dexBackedField.fieldIndex;
                                if (!z || fieldReference == null) {
                                    break;
                                }
                            } while (fieldReference.equals(immutableFieldReferenceOf));
                            return dexBackedField;
                        }
                    };
                }
            };
        }
        this.instanceFieldsOffset = this.staticFieldsOffset;
        return EmptySet.of();
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public Iterable<? extends DexBackedField> getInstanceFields() {
        return getInstanceFields(true);
    }

    public Iterable<? extends DexBackedField> getInstanceFields(final boolean z) {
        if (this.instanceFieldCount > 0) {
            DexReader<? extends DexBuffer> dexReader = this.dexFile.getDataBuffer().readerAt(getInstanceFieldsOffset());
            final AnnotationsDirectory annotationsDirectory = getAnnotationsDirectory();
            final int offset = dexReader.getOffset();
            HiddenApiRestrictionsReader hiddenApiRestrictionsReader = this.hiddenApiRestrictionsReader;
            final Iterator restrictionsForInstanceFields = hiddenApiRestrictionsReader == null ? null : hiddenApiRestrictionsReader.getRestrictionsForInstanceFields();
            return new Iterable<DexBackedField>() { // from class: org.jf.dexlib2.dexbacked.DexBackedClassDef.4
                @Override // java.lang.Iterable
                public Iterator<DexBackedField> iterator() {
                    final AnnotationsDirectory.AnnotationIterator fieldAnnotationIterator = annotationsDirectory.getFieldAnnotationIterator();
                    return new VariableSizeLookaheadIterator<DexBackedField>(DexBackedClassDef.this.dexFile.getDataBuffer(), offset) { // from class: org.jf.dexlib2.dexbacked.DexBackedClassDef.4.1
                        private int count;
                        private FieldReference previousField;
                        private int previousIndex;

                        @Override // org.jf.dexlib2.dexbacked.util.VariableSizeLookaheadIterator
                        protected /* bridge */ /* synthetic */ DexBackedField readNextItem(DexReader dexReader2) {
                            return readNextItem2((DexReader<?>) dexReader2);
                        }

                        @Override // org.jf.dexlib2.dexbacked.util.VariableSizeLookaheadIterator
                        /* JADX INFO: renamed from: readNextItem, reason: avoid collision after fix types in other method */
                        protected DexBackedField readNextItem2(DexReader<?> dexReader2) {
                            DexBackedField dexBackedField;
                            FieldReference fieldReference;
                            ImmutableFieldReference immutableFieldReferenceOf;
                            do {
                                int i = this.count + 1;
                                this.count = i;
                                if (i > DexBackedClassDef.this.instanceFieldCount) {
                                    DexBackedClassDef.this.directMethodsOffset = dexReader2.getOffset();
                                    return endOfData();
                                }
                                dexBackedField = new DexBackedField(DexBackedClassDef.this.dexFile, dexReader2, DexBackedClassDef.this, this.previousIndex, fieldAnnotationIterator, restrictionsForInstanceFields != null ? ((Integer) restrictionsForInstanceFields.next()).intValue() : 7);
                                fieldReference = this.previousField;
                                immutableFieldReferenceOf = ImmutableFieldReference.of(dexBackedField);
                                this.previousField = immutableFieldReferenceOf;
                                this.previousIndex = dexBackedField.fieldIndex;
                                if (!z || fieldReference == null) {
                                    break;
                                }
                            } while (fieldReference.equals(immutableFieldReferenceOf));
                            return dexBackedField;
                        }
                    };
                }
            };
        }
        int i = this.instanceFieldsOffset;
        if (i > 0) {
            this.directMethodsOffset = i;
        }
        return EmptySet.of();
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public Iterable<? extends DexBackedField> getFields() {
        return Iterables.concat(getStaticFields(), getInstanceFields());
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public Iterable<? extends DexBackedMethod> getDirectMethods() {
        return getDirectMethods(true);
    }

    public Iterable<? extends DexBackedMethod> getDirectMethods(final boolean z) {
        if (this.directMethodCount > 0) {
            DexReader<? extends DexBuffer> dexReader = this.dexFile.getDataBuffer().readerAt(getDirectMethodsOffset());
            final AnnotationsDirectory annotationsDirectory = getAnnotationsDirectory();
            final int offset = dexReader.getOffset();
            HiddenApiRestrictionsReader hiddenApiRestrictionsReader = this.hiddenApiRestrictionsReader;
            final Iterator restrictionsForDirectMethods = hiddenApiRestrictionsReader == null ? null : hiddenApiRestrictionsReader.getRestrictionsForDirectMethods();
            return new Iterable<DexBackedMethod>() { // from class: org.jf.dexlib2.dexbacked.DexBackedClassDef.5
                @Override // java.lang.Iterable
                public Iterator<DexBackedMethod> iterator() {
                    final AnnotationsDirectory.AnnotationIterator methodAnnotationIterator = annotationsDirectory.getMethodAnnotationIterator();
                    final AnnotationsDirectory.AnnotationIterator parameterAnnotationIterator = annotationsDirectory.getParameterAnnotationIterator();
                    return new VariableSizeLookaheadIterator<DexBackedMethod>(DexBackedClassDef.this.dexFile.getDataBuffer(), offset) { // from class: org.jf.dexlib2.dexbacked.DexBackedClassDef.5.1
                        private int count;
                        private int previousIndex;
                        private MethodReference previousMethod;

                        @Override // org.jf.dexlib2.dexbacked.util.VariableSizeLookaheadIterator
                        protected /* bridge */ /* synthetic */ DexBackedMethod readNextItem(DexReader dexReader2) {
                            return readNextItem2((DexReader<?>) dexReader2);
                        }

                        @Override // org.jf.dexlib2.dexbacked.util.VariableSizeLookaheadIterator
                        /* JADX INFO: renamed from: readNextItem, reason: avoid collision after fix types in other method */
                        protected DexBackedMethod readNextItem2(DexReader<?> dexReader2) {
                            DexBackedMethod dexBackedMethod;
                            MethodReference methodReference;
                            ImmutableMethodReference immutableMethodReferenceOf;
                            do {
                                int i = this.count + 1;
                                this.count = i;
                                if (i > DexBackedClassDef.this.directMethodCount) {
                                    DexBackedClassDef.this.virtualMethodsOffset = dexReader2.getOffset();
                                    return endOfData();
                                }
                                dexBackedMethod = new DexBackedMethod(DexBackedClassDef.this.dexFile, dexReader2, DexBackedClassDef.this, this.previousIndex, methodAnnotationIterator, parameterAnnotationIterator, restrictionsForDirectMethods != null ? ((Integer) restrictionsForDirectMethods.next()).intValue() : 7);
                                methodReference = this.previousMethod;
                                immutableMethodReferenceOf = ImmutableMethodReference.of(dexBackedMethod);
                                this.previousMethod = immutableMethodReferenceOf;
                                this.previousIndex = dexBackedMethod.methodIndex;
                                if (!z || methodReference == null) {
                                    break;
                                }
                            } while (methodReference.equals(immutableMethodReferenceOf));
                            return dexBackedMethod;
                        }
                    };
                }
            };
        }
        int i = this.directMethodsOffset;
        if (i > 0) {
            this.virtualMethodsOffset = i;
        }
        return EmptySet.of();
    }

    public Iterable<? extends DexBackedMethod> getVirtualMethods(boolean z) {
        if (this.virtualMethodCount > 0) {
            DexReader<? extends DexBuffer> dexReader = this.dexFile.getDataBuffer().readerAt(getVirtualMethodsOffset());
            AnnotationsDirectory annotationsDirectory = getAnnotationsDirectory();
            int offset = dexReader.getOffset();
            HiddenApiRestrictionsReader hiddenApiRestrictionsReader = this.hiddenApiRestrictionsReader;
            return new AnonymousClass6(annotationsDirectory, offset, hiddenApiRestrictionsReader == null ? null : hiddenApiRestrictionsReader.getRestrictionsForVirtualMethods(), z);
        }
        return EmptySet.of();
    }

    /* JADX INFO: renamed from: org.jf.dexlib2.dexbacked.DexBackedClassDef$6, reason: invalid class name */
    class AnonymousClass6 implements Iterable<DexBackedMethod> {
        final AnnotationsDirectory.AnnotationIterator methodAnnotationIterator;
        final AnnotationsDirectory.AnnotationIterator parameterAnnotationIterator;
        final /* synthetic */ AnnotationsDirectory val$annotationsDirectory;
        final /* synthetic */ Iterator val$hiddenApiRestrictionIterator;
        final /* synthetic */ int val$methodsStartOffset;
        final /* synthetic */ boolean val$skipDuplicates;

        AnonymousClass6(AnnotationsDirectory annotationsDirectory, int i, Iterator it, boolean z) {
            this.val$annotationsDirectory = annotationsDirectory;
            this.val$methodsStartOffset = i;
            this.val$hiddenApiRestrictionIterator = it;
            this.val$skipDuplicates = z;
            this.methodAnnotationIterator = annotationsDirectory.getMethodAnnotationIterator();
            this.parameterAnnotationIterator = annotationsDirectory.getParameterAnnotationIterator();
        }

        @Override // java.lang.Iterable
        public Iterator<DexBackedMethod> iterator() {
            return new VariableSizeLookaheadIterator<DexBackedMethod>(DexBackedClassDef.this.dexFile.getDataBuffer(), this.val$methodsStartOffset) { // from class: org.jf.dexlib2.dexbacked.DexBackedClassDef.6.1
                private int count;
                private int previousIndex;
                private MethodReference previousMethod;

                @Override // org.jf.dexlib2.dexbacked.util.VariableSizeLookaheadIterator
                protected /* bridge */ /* synthetic */ DexBackedMethod readNextItem(DexReader dexReader) {
                    return readNextItem2((DexReader<?>) dexReader);
                }

                @Override // org.jf.dexlib2.dexbacked.util.VariableSizeLookaheadIterator
                /* JADX INFO: renamed from: readNextItem, reason: avoid collision after fix types in other method */
                protected DexBackedMethod readNextItem2(DexReader<?> dexReader) {
                    DexBackedMethod dexBackedMethod;
                    MethodReference methodReference;
                    ImmutableMethodReference immutableMethodReferenceOf;
                    do {
                        int i = this.count + 1;
                        this.count = i;
                        if (i > DexBackedClassDef.this.virtualMethodCount) {
                            return endOfData();
                        }
                        dexBackedMethod = new DexBackedMethod(DexBackedClassDef.this.dexFile, dexReader, DexBackedClassDef.this, this.previousIndex, AnonymousClass6.this.methodAnnotationIterator, AnonymousClass6.this.parameterAnnotationIterator, AnonymousClass6.this.val$hiddenApiRestrictionIterator != null ? ((Integer) AnonymousClass6.this.val$hiddenApiRestrictionIterator.next()).intValue() : 7);
                        methodReference = this.previousMethod;
                        immutableMethodReferenceOf = ImmutableMethodReference.of(dexBackedMethod);
                        this.previousMethod = immutableMethodReferenceOf;
                        this.previousIndex = dexBackedMethod.methodIndex;
                        if (!AnonymousClass6.this.val$skipDuplicates || methodReference == null) {
                            break;
                        }
                    } while (methodReference.equals(immutableMethodReferenceOf));
                    return dexBackedMethod;
                }
            };
        }
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public Iterable<? extends DexBackedMethod> getVirtualMethods() {
        return getVirtualMethods(true);
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public Iterable<? extends DexBackedMethod> getMethods() {
        return Iterables.concat(getDirectMethods(), getVirtualMethods());
    }

    private AnnotationsDirectory getAnnotationsDirectory() {
        if (this.annotationsDirectory == null) {
            this.annotationsDirectory = AnnotationsDirectory.newOrEmpty(this.dexFile, this.dexFile.getBuffer().readSmallUint(this.classDefOffset + 20));
        }
        return this.annotationsDirectory;
    }

    private int getInstanceFieldsOffset() {
        int i = this.instanceFieldsOffset;
        if (i > 0) {
            return i;
        }
        DexReader<? extends DexBuffer> dexReader = this.dexFile.getDataBuffer().readerAt(this.staticFieldsOffset);
        DexBackedField.skipFields(dexReader, this.staticFieldCount);
        int offset = dexReader.getOffset();
        this.instanceFieldsOffset = offset;
        return offset;
    }

    private int getDirectMethodsOffset() {
        int i = this.directMethodsOffset;
        if (i > 0) {
            return i;
        }
        DexReader<? extends DexBuffer> dexReader = this.dexFile.getDataBuffer().readerAt(getInstanceFieldsOffset());
        DexBackedField.skipFields(dexReader, this.instanceFieldCount);
        int offset = dexReader.getOffset();
        this.directMethodsOffset = offset;
        return offset;
    }

    private int getVirtualMethodsOffset() {
        int i = this.virtualMethodsOffset;
        if (i > 0) {
            return i;
        }
        DexReader<? extends DexBuffer> dexReader = this.dexFile.getDataBuffer().readerAt(getDirectMethodsOffset());
        DexBackedMethod.skipMethods(dexReader, this.directMethodCount);
        int offset = dexReader.getOffset();
        this.virtualMethodsOffset = offset;
        return offset;
    }

    public int getSize() {
        int size = getInterfaces().size();
        int size2 = size > 0 ? 40 + (size * 2) : 36;
        AnnotationsDirectory annotationsDirectory = getAnnotationsDirectory();
        if (!AnnotationsDirectory.EMPTY.equals(annotationsDirectory)) {
            int i = size2 + 16;
            Set<? extends DexBackedAnnotation> classAnnotations = annotationsDirectory.getClassAnnotations();
            size2 = !classAnnotations.isEmpty() ? size2 + 20 + (classAnnotations.size() * 4) : i;
        }
        int smallUint = this.dexFile.getBuffer().readSmallUint(this.classDefOffset + 28);
        if (smallUint != 0) {
            size2 += this.dexFile.getDataBuffer().readerAt(smallUint).peekSmallUleb128Size();
        }
        int smallUint2 = this.dexFile.getBuffer().readSmallUint(this.classDefOffset + 24);
        if (smallUint2 > 0) {
            DexReader<? extends DexBuffer> dexReader = this.dexFile.getDataBuffer().readerAt(smallUint2);
            dexReader.readSmallUleb128();
            dexReader.readSmallUleb128();
            dexReader.readSmallUleb128();
            dexReader.readSmallUleb128();
            size2 += dexReader.getOffset() - smallUint2;
        }
        Iterator<? extends DexBackedField> it = getFields().iterator();
        while (it.hasNext()) {
            size2 += it.next().getSize();
        }
        Iterator<? extends DexBackedMethod> it2 = getMethods().iterator();
        while (it2.hasNext()) {
            size2 += it2.next().getSize();
        }
        return size2;
    }

    private class HiddenApiRestrictionsReader {
        private int directMethodsStartOffset;
        private int instanceFieldsStartOffset;
        private final int startOffset;
        private int virtualMethodsStartOffset;

        public HiddenApiRestrictionsReader(int i) {
            this.startOffset = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public VariableSizeListIterator<Integer> getRestrictionsForStaticFields() {
            return new VariableSizeListIterator<Integer>(DexBackedClassDef.this.dexFile.getDataBuffer(), this.startOffset, DexBackedClassDef.this.staticFieldCount) { // from class: org.jf.dexlib2.dexbacked.DexBackedClassDef.HiddenApiRestrictionsReader.1
                @Override // org.jf.dexlib2.dexbacked.util.VariableSizeListIterator
                protected /* bridge */ /* synthetic */ Integer readNextItem(DexReader dexReader, int i) {
                    return readNextItem((DexReader<? extends DexBuffer>) dexReader, i);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // org.jf.dexlib2.dexbacked.util.VariableSizeListIterator
                protected Integer readNextItem(DexReader<? extends DexBuffer> dexReader, int i) {
                    return Integer.valueOf(dexReader.readSmallUleb128());
                }

                @Override // org.jf.dexlib2.dexbacked.util.VariableSizeListIterator, java.util.ListIterator, java.util.Iterator
                public Integer next() {
                    if (nextIndex() == DexBackedClassDef.this.staticFieldCount) {
                        HiddenApiRestrictionsReader.this.instanceFieldsStartOffset = getReaderOffset();
                    }
                    return (Integer) super.next();
                }
            };
        }

        private int getInstanceFieldsStartOffset() {
            if (this.instanceFieldsStartOffset == 0) {
                DexReader<? extends DexBuffer> dexReader = DexBackedClassDef.this.dexFile.getDataBuffer().readerAt(this.startOffset);
                for (int i = 0; i < DexBackedClassDef.this.staticFieldCount; i++) {
                    dexReader.readSmallUleb128();
                }
                this.instanceFieldsStartOffset = dexReader.getOffset();
            }
            return this.instanceFieldsStartOffset;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Iterator<Integer> getRestrictionsForInstanceFields() {
            return new VariableSizeListIterator<Integer>(DexBackedClassDef.this.dexFile.getDataBuffer(), getInstanceFieldsStartOffset(), DexBackedClassDef.this.instanceFieldCount) { // from class: org.jf.dexlib2.dexbacked.DexBackedClassDef.HiddenApiRestrictionsReader.2
                @Override // org.jf.dexlib2.dexbacked.util.VariableSizeListIterator
                protected /* bridge */ /* synthetic */ Integer readNextItem(DexReader dexReader, int i) {
                    return readNextItem((DexReader<? extends DexBuffer>) dexReader, i);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // org.jf.dexlib2.dexbacked.util.VariableSizeListIterator
                protected Integer readNextItem(DexReader<? extends DexBuffer> dexReader, int i) {
                    return Integer.valueOf(dexReader.readSmallUleb128());
                }

                @Override // org.jf.dexlib2.dexbacked.util.VariableSizeListIterator, java.util.ListIterator, java.util.Iterator
                public Integer next() {
                    if (nextIndex() == DexBackedClassDef.this.instanceFieldCount) {
                        HiddenApiRestrictionsReader.this.directMethodsStartOffset = getReaderOffset();
                    }
                    return (Integer) super.next();
                }
            };
        }

        private int getDirectMethodsStartOffset() {
            if (this.directMethodsStartOffset == 0) {
                DexReader<? extends DexBuffer> dexReader = DexBackedClassDef.this.dexFile.getDataBuffer().readerAt(getInstanceFieldsStartOffset());
                for (int i = 0; i < DexBackedClassDef.this.instanceFieldCount; i++) {
                    dexReader.readSmallUleb128();
                }
                this.directMethodsStartOffset = dexReader.getOffset();
            }
            return this.directMethodsStartOffset;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Iterator<Integer> getRestrictionsForDirectMethods() {
            return new VariableSizeListIterator<Integer>(DexBackedClassDef.this.dexFile.getDataBuffer(), getDirectMethodsStartOffset(), DexBackedClassDef.this.directMethodCount) { // from class: org.jf.dexlib2.dexbacked.DexBackedClassDef.HiddenApiRestrictionsReader.3
                @Override // org.jf.dexlib2.dexbacked.util.VariableSizeListIterator
                protected /* bridge */ /* synthetic */ Integer readNextItem(DexReader dexReader, int i) {
                    return readNextItem((DexReader<? extends DexBuffer>) dexReader, i);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // org.jf.dexlib2.dexbacked.util.VariableSizeListIterator
                protected Integer readNextItem(DexReader<? extends DexBuffer> dexReader, int i) {
                    return Integer.valueOf(dexReader.readSmallUleb128());
                }

                @Override // org.jf.dexlib2.dexbacked.util.VariableSizeListIterator, java.util.ListIterator, java.util.Iterator
                public Integer next() {
                    if (nextIndex() == DexBackedClassDef.this.directMethodCount) {
                        HiddenApiRestrictionsReader.this.virtualMethodsStartOffset = getReaderOffset();
                    }
                    return (Integer) super.next();
                }
            };
        }

        private int getVirtualMethodsStartOffset() {
            if (this.virtualMethodsStartOffset == 0) {
                DexReader<? extends DexBuffer> dexReader = DexBackedClassDef.this.dexFile.getDataBuffer().readerAt(getDirectMethodsStartOffset());
                for (int i = 0; i < DexBackedClassDef.this.directMethodCount; i++) {
                    dexReader.readSmallUleb128();
                }
                this.virtualMethodsStartOffset = dexReader.getOffset();
            }
            return this.virtualMethodsStartOffset;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Iterator<Integer> getRestrictionsForVirtualMethods() {
            return new VariableSizeListIterator<Integer>(DexBackedClassDef.this.dexFile.getDataBuffer(), getVirtualMethodsStartOffset(), DexBackedClassDef.this.virtualMethodCount) { // from class: org.jf.dexlib2.dexbacked.DexBackedClassDef.HiddenApiRestrictionsReader.4
                @Override // org.jf.dexlib2.dexbacked.util.VariableSizeListIterator
                protected /* bridge */ /* synthetic */ Integer readNextItem(DexReader dexReader, int i) {
                    return readNextItem((DexReader<? extends DexBuffer>) dexReader, i);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // org.jf.dexlib2.dexbacked.util.VariableSizeListIterator
                protected Integer readNextItem(DexReader<? extends DexBuffer> dexReader, int i) {
                    return Integer.valueOf(dexReader.readSmallUleb128());
                }
            };
        }
    }
}
