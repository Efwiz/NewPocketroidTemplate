package com.reandroid.dex.smali.formatters;

import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.model.DexClass;
import com.reandroid.dex.model.DexClassRepository;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.utils.ObjectsUtil;
import java.io.IOException;
import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public interface ClassComment extends SmaliComment {
    void writeComment(SmaliWriter smaliWriter, TypeKey typeKey) throws IOException;

    public static class ClassExtendComment implements ClassComment {
        private final DexClassRepository classRepository;

        public ClassExtendComment(DexClassRepository dexClassRepository) {
            this.classRepository = dexClassRepository;
        }

        @Override // com.reandroid.dex.smali.formatters.ClassComment
        public void writeComment(SmaliWriter smaliWriter, TypeKey typeKey) throws IOException {
            DexClass dexClass = this.classRepository.getDexClass(typeKey);
            if (dexClass == null || dexClass.isFinal()) {
                return;
            }
            Iterator<DexClass> extending = dexClass.getExtending();
            while (extending.hasNext()) {
                TypeKey key = extending.next().getKey();
                smaliWriter.appendComment("extended-by: ");
                smaliWriter.appendComment(key.getTypeName());
                smaliWriter.newLine();
            }
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof ClassExtendComment) && this.classRepository == ((ClassExtendComment) obj).classRepository;
        }

        public int hashCode() {
            return ObjectsUtil.hash(this.classRepository, getClass());
        }
    }

    public static class ClassImplementComment implements ClassComment {
        private final DexClassRepository classRepository;

        public ClassImplementComment(DexClassRepository dexClassRepository) {
            this.classRepository = dexClassRepository;
        }

        @Override // com.reandroid.dex.smali.formatters.ClassComment
        public void writeComment(SmaliWriter smaliWriter, TypeKey typeKey) throws IOException {
            DexClass dexClass = this.classRepository.getDexClass(typeKey);
            if (dexClass == null || !dexClass.isInterface()) {
                return;
            }
            Iterator<DexClass> implementations = dexClass.getImplementations();
            while (implementations.hasNext()) {
                TypeKey key = implementations.next().getKey();
                smaliWriter.appendComment("implemented-by: ");
                smaliWriter.appendComment(key.getTypeName());
                smaliWriter.newLine();
            }
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof ClassImplementComment) && this.classRepository == ((ClassImplementComment) obj).classRepository;
        }

        public int hashCode() {
            return ObjectsUtil.hash(this.classRepository, getClass());
        }
    }
}
