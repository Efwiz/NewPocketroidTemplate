package org.apache.xerces.xs;

/* JADX INFO: loaded from: classes13.dex */
public interface PSVIProvider {
    AttributePSVI getAttributePSVI(int i);

    AttributePSVI getAttributePSVIByName(String str, String str2);

    ElementPSVI getElementPSVI();
}
