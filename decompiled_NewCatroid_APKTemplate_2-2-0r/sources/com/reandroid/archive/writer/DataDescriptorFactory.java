package com.reandroid.archive.writer;

import com.reandroid.archive.Archive;
import com.reandroid.archive.block.LocalFileHeader;

/* JADX INFO: loaded from: classes10.dex */
public interface DataDescriptorFactory {
    public static final DataDescriptorFactory NO_ACTION = new DataDescriptorFactory() { // from class: com.reandroid.archive.writer.DataDescriptorFactory$$ExternalSyntheticLambda0
        @Override // com.reandroid.archive.writer.DataDescriptorFactory
        public final void createDataDescriptor(LocalFileHeader localFileHeader) {
            localFileHeader.setHasDataDescriptor(localFileHeader.hasDataDescriptor());
        }
    };
    public static final DataDescriptorFactory NONE = new DataDescriptorFactory() { // from class: com.reandroid.archive.writer.DataDescriptorFactory$$ExternalSyntheticLambda1
        @Override // com.reandroid.archive.writer.DataDescriptorFactory
        public final void createDataDescriptor(LocalFileHeader localFileHeader) {
            localFileHeader.setHasDataDescriptor(false);
        }
    };
    public static final DataDescriptorFactory FOR_DEFLATED = new DataDescriptorFactory() { // from class: com.reandroid.archive.writer.DataDescriptorFactory$$ExternalSyntheticLambda2
        @Override // com.reandroid.archive.writer.DataDescriptorFactory
        public final void createDataDescriptor(LocalFileHeader localFileHeader) {
            localFileHeader.setHasDataDescriptor(localFileHeader.getMethod() == Archive.DEFLATED);
        }
    };
    public static final DataDescriptorFactory FOR_STORED = new DataDescriptorFactory() { // from class: com.reandroid.archive.writer.DataDescriptorFactory$$ExternalSyntheticLambda3
        @Override // com.reandroid.archive.writer.DataDescriptorFactory
        public final void createDataDescriptor(LocalFileHeader localFileHeader) {
            localFileHeader.setHasDataDescriptor(localFileHeader.getMethod() == Archive.STORED);
        }
    };
    public static final DataDescriptorFactory FOR_ALL = new DataDescriptorFactory() { // from class: com.reandroid.archive.writer.DataDescriptorFactory$$ExternalSyntheticLambda4
        @Override // com.reandroid.archive.writer.DataDescriptorFactory
        public final void createDataDescriptor(LocalFileHeader localFileHeader) {
            localFileHeader.setHasDataDescriptor(true);
        }
    };

    void createDataDescriptor(LocalFileHeader localFileHeader);
}
