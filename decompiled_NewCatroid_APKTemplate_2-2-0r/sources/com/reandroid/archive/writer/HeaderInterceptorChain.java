package com.reandroid.archive.writer;

import com.reandroid.archive.ArchiveInfo;
import com.reandroid.archive.block.CentralEntryHeader;
import com.reandroid.archive.block.DataDescriptor;
import com.reandroid.archive.block.LocalFileHeader;

/* JADX INFO: loaded from: classes10.dex */
public class HeaderInterceptorChain implements HeaderInterceptor {
    private ArchiveInfo archiveInfo;
    private DataDescriptorFactory dataDescriptorFactory;
    private HeaderInterceptor headerInterceptor;

    private HeaderInterceptorChain() {
    }

    public DataDescriptorFactory getDataDescriptorFactory() {
        DataDescriptorFactory dataDescriptorFactory = this.dataDescriptorFactory;
        if (dataDescriptorFactory != null) {
            return dataDescriptorFactory;
        }
        DataDescriptorFactory dataDescriptorFactory2 = DataDescriptorFactory.NO_ACTION;
        this.dataDescriptorFactory = dataDescriptorFactory2;
        return dataDescriptorFactory2;
    }

    public void setDataDescriptorFactory(DataDescriptorFactory dataDescriptorFactory) {
        this.dataDescriptorFactory = dataDescriptorFactory;
    }

    public ArchiveInfo getArchiveInfo() {
        return this.archiveInfo;
    }

    public void setArchiveInfo(ArchiveInfo archiveInfo) {
        this.archiveInfo = archiveInfo;
    }

    public boolean isDisabled() {
        return getArchiveInfo() == null && getHeaderInterceptor() == null && getDataDescriptorFactory() == DataDescriptorFactory.NO_ACTION;
    }

    public void setHeaderInterceptor(HeaderInterceptor headerInterceptor) {
        this.headerInterceptor = headerInterceptor;
    }

    public HeaderInterceptor getHeaderInterceptor() {
        return this.headerInterceptor;
    }

    @Override // com.reandroid.archive.writer.HeaderInterceptor
    public void onWriteLfh(LocalFileHeader localFileHeader) {
        getDataDescriptorFactory().createDataDescriptor(localFileHeader);
        ArchiveInfo archiveInfo = getArchiveInfo();
        if (archiveInfo != null) {
            archiveInfo.onWriteLfh(localFileHeader);
        }
        HeaderInterceptor headerInterceptor = getHeaderInterceptor();
        if (headerInterceptor != null) {
            headerInterceptor.onWriteLfh(localFileHeader);
        }
    }

    @Override // com.reandroid.archive.writer.HeaderInterceptor
    public void onWriteDD(DataDescriptor dataDescriptor) {
        ArchiveInfo archiveInfo = getArchiveInfo();
        if (archiveInfo != null) {
            archiveInfo.onWriteDD(dataDescriptor);
        }
        HeaderInterceptor headerInterceptor = getHeaderInterceptor();
        if (headerInterceptor != null) {
            headerInterceptor.onWriteDD(dataDescriptor);
        }
    }

    @Override // com.reandroid.archive.writer.HeaderInterceptor
    public void onWriteCeh(CentralEntryHeader centralEntryHeader) {
        ArchiveInfo archiveInfo = getArchiveInfo();
        if (archiveInfo != null) {
            archiveInfo.onWriteCeh(centralEntryHeader);
        }
        HeaderInterceptor headerInterceptor = getHeaderInterceptor();
        if (headerInterceptor != null) {
            headerInterceptor.onWriteCeh(centralEntryHeader);
        }
    }

    public static HeaderInterceptorChain createDefault() {
        HeaderInterceptorChain headerInterceptorChain = new HeaderInterceptorChain();
        headerInterceptorChain.setArchiveInfo(ArchiveInfo.apk());
        headerInterceptorChain.setDataDescriptorFactory(DataDescriptorFactory.NO_ACTION);
        return headerInterceptorChain;
    }
}
