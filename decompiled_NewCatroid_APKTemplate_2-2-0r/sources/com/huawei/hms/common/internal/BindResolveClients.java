package com.huawei.hms.common.internal;

import java.util.ArrayList;
import java.util.ListIterator;

/* JADX INFO: loaded from: classes10.dex */
public class BindResolveClients {
    private static final Object b = new Object();
    private ArrayList<ResolveClientBean> a;

    private static class SingletonManager {
        private static final BindResolveClients a = new BindResolveClients();

        private SingletonManager() {
        }
    }

    public static BindResolveClients getInstance() {
        return SingletonManager.a;
    }

    public boolean isClientRegistered(ResolveClientBean resolveClientBean) {
        boolean zContains;
        synchronized (b) {
            zContains = this.a.contains(resolveClientBean);
        }
        return zContains;
    }

    public void notifyClientReconnect() {
        synchronized (b) {
            ListIterator<ResolveClientBean> listIterator = this.a.listIterator();
            while (listIterator.hasNext()) {
                listIterator.next().clientReconnect();
            }
            this.a.clear();
        }
    }

    public void register(ResolveClientBean resolveClientBean) {
        if (resolveClientBean == null) {
            return;
        }
        synchronized (b) {
            if (!this.a.contains(resolveClientBean)) {
                this.a.add(resolveClientBean);
            }
        }
    }

    public void unRegister(ResolveClientBean resolveClientBean) {
        if (resolveClientBean == null) {
            return;
        }
        synchronized (b) {
            if (this.a.contains(resolveClientBean)) {
                ListIterator<ResolveClientBean> listIterator = this.a.listIterator();
                while (listIterator.hasNext()) {
                    if (resolveClientBean.equals(listIterator.next())) {
                        listIterator.remove();
                        break;
                    }
                }
            }
        }
    }

    public void unRegisterAll() {
        synchronized (b) {
            this.a.clear();
        }
    }

    private BindResolveClients() {
        this.a = new ArrayList<>();
    }
}
