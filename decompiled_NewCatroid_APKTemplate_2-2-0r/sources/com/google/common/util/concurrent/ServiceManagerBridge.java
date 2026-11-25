package com.google.common.util.concurrent;

import com.google.common.collect.ImmutableMultimap;

/* JADX INFO: loaded from: classes10.dex */
@ElementTypesAreNonnullByDefault
interface ServiceManagerBridge {
    ImmutableMultimap<Service.State, Service> servicesByState();
}
