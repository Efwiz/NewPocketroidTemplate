package org.eclipse.jgit.transport;

/* JADX INFO: loaded from: classes13.dex */
public interface ProtocolV2Hook {
    public static final ProtocolV2Hook DEFAULT = new ProtocolV2Hook() { // from class: org.eclipse.jgit.transport.ProtocolV2Hook.1
    };

    default void onCapabilities(CapabilitiesV2Request capabilitiesV2Request) throws ServiceMayNotContinueException {
    }

    default void onFetch(FetchV2Request fetchV2Request) throws ServiceMayNotContinueException {
    }

    default void onLsRefs(LsRefsV2Request lsRefsV2Request) throws ServiceMayNotContinueException {
    }

    default void onObjectInfo(ObjectInfoRequest objectInfoRequest) throws ServiceMayNotContinueException {
    }
}
