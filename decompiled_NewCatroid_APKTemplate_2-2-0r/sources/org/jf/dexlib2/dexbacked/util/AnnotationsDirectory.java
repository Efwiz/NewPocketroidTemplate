package org.jf.dexlib2.dexbacked.util;

import java.util.List;
import java.util.Set;
import org.jf.dexlib2.dexbacked.DexBackedAnnotation;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.util.collection.EmptyList;
import org.jf.util.collection.EmptySet;

/* JADX INFO: loaded from: classes6.dex */
public abstract class AnnotationsDirectory {
    public static final AnnotationsDirectory EMPTY = new AnnotationsDirectory() { // from class: org.jf.dexlib2.dexbacked.util.AnnotationsDirectory.1
        @Override // org.jf.dexlib2.dexbacked.util.AnnotationsDirectory
        public int getFieldAnnotationCount() {
            return 0;
        }

        @Override // org.jf.dexlib2.dexbacked.util.AnnotationsDirectory
        public Set<? extends DexBackedAnnotation> getClassAnnotations() {
            return EmptySet.of();
        }

        @Override // org.jf.dexlib2.dexbacked.util.AnnotationsDirectory
        public AnnotationIterator getFieldAnnotationIterator() {
            return AnnotationIterator.EMPTY;
        }

        @Override // org.jf.dexlib2.dexbacked.util.AnnotationsDirectory
        public AnnotationIterator getMethodAnnotationIterator() {
            return AnnotationIterator.EMPTY;
        }

        @Override // org.jf.dexlib2.dexbacked.util.AnnotationsDirectory
        public AnnotationIterator getParameterAnnotationIterator() {
            return AnnotationIterator.EMPTY;
        }
    };

    public interface AnnotationIterator {
        public static final AnnotationIterator EMPTY = new AnnotationIterator() { // from class: org.jf.dexlib2.dexbacked.util.AnnotationsDirectory.AnnotationIterator.1
            @Override // org.jf.dexlib2.dexbacked.util.AnnotationsDirectory.AnnotationIterator
            public void reset() {
            }

            @Override // org.jf.dexlib2.dexbacked.util.AnnotationsDirectory.AnnotationIterator
            public int seekTo(int i) {
                return 0;
            }
        };

        void reset();

        int seekTo(int i);
    }

    public abstract Set<? extends DexBackedAnnotation> getClassAnnotations();

    public abstract int getFieldAnnotationCount();

    public abstract AnnotationIterator getFieldAnnotationIterator();

    public abstract AnnotationIterator getMethodAnnotationIterator();

    public abstract AnnotationIterator getParameterAnnotationIterator();

    public static AnnotationsDirectory newOrEmpty(DexBackedDexFile dexBackedDexFile, int i) {
        if (i == 0) {
            return EMPTY;
        }
        return new AnnotationsDirectoryImpl(dexBackedDexFile, i);
    }

    public static Set<? extends DexBackedAnnotation> getAnnotations(final DexBackedDexFile dexBackedDexFile, final int i) {
        if (i != 0) {
            final int smallUint = dexBackedDexFile.getDataBuffer().readSmallUint(i);
            return new FixedSizeSet<DexBackedAnnotation>() { // from class: org.jf.dexlib2.dexbacked.util.AnnotationsDirectory.2
                @Override // org.jf.dexlib2.dexbacked.util.FixedSizeSet
                public DexBackedAnnotation readItem(int i2) {
                    return new DexBackedAnnotation(dexBackedDexFile, dexBackedDexFile.getDataBuffer().readSmallUint(i + 4 + (i2 * 4)));
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
                public int size() {
                    return smallUint;
                }
            };
        }
        return EmptySet.of();
    }

    public static List<Set<? extends DexBackedAnnotation>> getParameterAnnotations(final DexBackedDexFile dexBackedDexFile, final int i) {
        if (i > 0) {
            final int smallUint = dexBackedDexFile.getDataBuffer().readSmallUint(i);
            return new FixedSizeList<Set<? extends DexBackedAnnotation>>() { // from class: org.jf.dexlib2.dexbacked.util.AnnotationsDirectory.3
                @Override // org.jf.dexlib2.dexbacked.util.FixedSizeList
                public Set<? extends DexBackedAnnotation> readItem(int i2) {
                    return AnnotationsDirectory.getAnnotations(dexBackedDexFile, dexBackedDexFile.getDataBuffer().readSmallUint(i + 4 + (i2 * 4)));
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
                public int size() {
                    return smallUint;
                }
            };
        }
        return EmptyList.of();
    }

    private static class AnnotationsDirectoryImpl extends AnnotationsDirectory {
        private static final int ANNOTATIONS_START_OFFSET = 16;
        private static final int FIELD_ANNOTATION_SIZE = 8;
        private static final int FIELD_COUNT_OFFSET = 4;
        private static final int METHOD_ANNOTATION_SIZE = 8;
        private static final int METHOD_COUNT_OFFSET = 8;
        private static final int PARAMETER_COUNT_OFFSET = 12;
        public final DexBackedDexFile dexFile;
        private final int directoryOffset;

        public AnnotationsDirectoryImpl(DexBackedDexFile dexBackedDexFile, int i) {
            this.dexFile = dexBackedDexFile;
            this.directoryOffset = i;
        }

        @Override // org.jf.dexlib2.dexbacked.util.AnnotationsDirectory
        public int getFieldAnnotationCount() {
            return this.dexFile.getDataBuffer().readSmallUint(this.directoryOffset + 4);
        }

        public int getMethodAnnotationCount() {
            return this.dexFile.getDataBuffer().readSmallUint(this.directoryOffset + 8);
        }

        public int getParameterAnnotationCount() {
            return this.dexFile.getDataBuffer().readSmallUint(this.directoryOffset + 12);
        }

        @Override // org.jf.dexlib2.dexbacked.util.AnnotationsDirectory
        public Set<? extends DexBackedAnnotation> getClassAnnotations() {
            DexBackedDexFile dexBackedDexFile = this.dexFile;
            return getAnnotations(dexBackedDexFile, dexBackedDexFile.getDataBuffer().readSmallUint(this.directoryOffset));
        }

        @Override // org.jf.dexlib2.dexbacked.util.AnnotationsDirectory
        public AnnotationIterator getFieldAnnotationIterator() {
            int fieldAnnotationCount = getFieldAnnotationCount();
            if (fieldAnnotationCount == 0) {
                return AnnotationIterator.EMPTY;
            }
            return new AnnotationIteratorImpl(this.directoryOffset + 16, fieldAnnotationCount);
        }

        @Override // org.jf.dexlib2.dexbacked.util.AnnotationsDirectory
        public AnnotationIterator getMethodAnnotationIterator() {
            int methodAnnotationCount = getMethodAnnotationCount();
            if (methodAnnotationCount == 0) {
                return AnnotationIterator.EMPTY;
            }
            return new AnnotationIteratorImpl(this.directoryOffset + 16 + (getFieldAnnotationCount() * 8), methodAnnotationCount);
        }

        @Override // org.jf.dexlib2.dexbacked.util.AnnotationsDirectory
        public AnnotationIterator getParameterAnnotationIterator() {
            int parameterAnnotationCount = getParameterAnnotationCount();
            if (parameterAnnotationCount == 0) {
                return AnnotationIterator.EMPTY;
            }
            return new AnnotationIteratorImpl(this.directoryOffset + 16 + (getFieldAnnotationCount() * 8) + (getMethodAnnotationCount() * 8), parameterAnnotationCount);
        }

        private class AnnotationIteratorImpl implements AnnotationIterator {
            private int currentIndex = 0;
            private int currentItemIndex;
            private final int size;
            private final int startOffset;

            public AnnotationIteratorImpl(int i, int i2) {
                this.startOffset = i;
                this.size = i2;
                this.currentItemIndex = AnnotationsDirectoryImpl.this.dexFile.getDataBuffer().readSmallUint(i);
            }

            @Override // org.jf.dexlib2.dexbacked.util.AnnotationsDirectory.AnnotationIterator
            public int seekTo(int i) {
                int i2;
                while (true) {
                    i2 = this.currentItemIndex;
                    if (i2 >= i) {
                        break;
                    }
                    int i3 = this.currentIndex;
                    if (i3 + 1 >= this.size) {
                        break;
                    }
                    this.currentIndex = i3 + 1;
                    this.currentItemIndex = AnnotationsDirectoryImpl.this.dexFile.getDataBuffer().readSmallUint(this.startOffset + (this.currentIndex * 8));
                }
                if (i2 == i) {
                    return AnnotationsDirectoryImpl.this.dexFile.getDataBuffer().readSmallUint(this.startOffset + (this.currentIndex * 8) + 4);
                }
                return 0;
            }

            @Override // org.jf.dexlib2.dexbacked.util.AnnotationsDirectory.AnnotationIterator
            public void reset() {
                this.currentItemIndex = AnnotationsDirectoryImpl.this.dexFile.getDataBuffer().readSmallUint(this.startOffset);
                this.currentIndex = 0;
            }
        }
    }
}
