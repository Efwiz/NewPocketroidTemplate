package com.hivemq.client.internal.mqtt.ioc;

import dagger.Component;
import javax.inject.Singleton;

/* JADX INFO: loaded from: classes10.dex */
@Component
@Singleton
public interface SingletonComponent {
    public static final SingletonComponent INSTANCE = DaggerSingletonComponent.create();

    ClientComponent.Builder clientComponentBuilder();
}
