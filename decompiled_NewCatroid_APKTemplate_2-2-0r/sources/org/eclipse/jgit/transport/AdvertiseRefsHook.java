package org.eclipse.jgit.transport;

/* JADX INFO: loaded from: classes13.dex */
public interface AdvertiseRefsHook {
    public static final AdvertiseRefsHook DEFAULT = new AdvertiseRefsHook() { // from class: org.eclipse.jgit.transport.AdvertiseRefsHook.1
        @Override // org.eclipse.jgit.transport.AdvertiseRefsHook
        public void advertiseRefs(ReceivePack receivePack) {
        }

        @Override // org.eclipse.jgit.transport.AdvertiseRefsHook
        public void advertiseRefs(UploadPack uploadPack) {
        }
    };

    void advertiseRefs(ReceivePack receivePack) throws ServiceMayNotContinueException;

    void advertiseRefs(UploadPack uploadPack) throws ServiceMayNotContinueException;
}
