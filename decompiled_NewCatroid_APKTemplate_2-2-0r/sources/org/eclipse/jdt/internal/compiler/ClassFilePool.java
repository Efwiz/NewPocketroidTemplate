package org.eclipse.jdt.internal.compiler;

import java.util.Arrays;
import org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class ClassFilePool {
    public static final int POOL_SIZE = 25;
    ClassFile[] classFiles = new ClassFile[25];

    private ClassFilePool() {
    }

    public static ClassFilePool newInstance() {
        return new ClassFilePool();
    }

    public synchronized ClassFile acquire(SourceTypeBinding sourceTypeBinding) {
        for (int i = 0; i < 25; i++) {
            ClassFile classFile = this.classFiles[i];
            if (classFile == null) {
                ClassFile classFile2 = new ClassFile(sourceTypeBinding);
                this.classFiles[i] = classFile2;
                classFile2.isShared = true;
                return classFile2;
            }
            if (!classFile.isShared) {
                classFile.reset(sourceTypeBinding);
                classFile.isShared = true;
                return classFile;
            }
        }
        return new ClassFile(sourceTypeBinding);
    }

    public synchronized void release(ClassFile classFile) {
        classFile.isShared = false;
    }

    public void reset() {
        Arrays.fill(this.classFiles, (Object) null);
    }
}
