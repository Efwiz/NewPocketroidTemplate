package com.huawei.hms.mlkit.tts.common;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.huawei.hms.feature.dynamic.IObjectWrapper;

/* JADX INFO: loaded from: classes10.dex */
public interface IRemoteTtsDelegate extends IInterface {
    public static final String DESCRIPTOR = "com.huawei.hms.mlkit.tts.common.IRemoteTtsDelegate";

    public static class Default implements IRemoteTtsDelegate {
        @Override // com.huawei.hms.mlkit.tts.common.IRemoteTtsDelegate
        public void addCallback(IRemoteTtsCallback iRemoteTtsCallback) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.huawei.hms.mlkit.tts.common.IRemoteTtsDelegate
        public int destroy() throws RemoteException {
            return 0;
        }

        @Override // com.huawei.hms.mlkit.tts.common.IRemoteTtsDelegate
        public int getModelLevel() throws RemoteException {
            return 0;
        }

        @Override // com.huawei.hms.mlkit.tts.common.IRemoteTtsDelegate
        public int initialize(IObjectWrapper iObjectWrapper, TtsOptionsParcel ttsOptionsParcel) throws RemoteException {
            return 0;
        }

        @Override // com.huawei.hms.mlkit.tts.common.IRemoteTtsDelegate
        public void startSynthesis(TtsTextParcel ttsTextParcel) throws RemoteException {
        }

        @Override // com.huawei.hms.mlkit.tts.common.IRemoteTtsDelegate
        public void stopSynthesis(TtsCancelParcel ttsCancelParcel) throws RemoteException {
        }

        @Override // com.huawei.hms.mlkit.tts.common.IRemoteTtsDelegate
        public void updateConfig(TtsOptionsParcel ttsOptionsParcel) throws RemoteException {
        }
    }

    void addCallback(IRemoteTtsCallback iRemoteTtsCallback) throws RemoteException;

    int destroy() throws RemoteException;

    int getModelLevel() throws RemoteException;

    int initialize(IObjectWrapper iObjectWrapper, TtsOptionsParcel ttsOptionsParcel) throws RemoteException;

    void startSynthesis(TtsTextParcel ttsTextParcel) throws RemoteException;

    void stopSynthesis(TtsCancelParcel ttsCancelParcel) throws RemoteException;

    void updateConfig(TtsOptionsParcel ttsOptionsParcel) throws RemoteException;

    public static abstract class Stub extends Binder implements IRemoteTtsDelegate {
        static final int TRANSACTION_addCallback = 4;
        static final int TRANSACTION_destroy = 7;
        static final int TRANSACTION_getModelLevel = 2;
        static final int TRANSACTION_initialize = 1;
        static final int TRANSACTION_startSynthesis = 5;
        static final int TRANSACTION_stopSynthesis = 6;
        static final int TRANSACTION_updateConfig = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IRemoteTtsDelegate.DESCRIPTOR);
        }

        public static IRemoteTtsDelegate asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IRemoteTtsDelegate.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRemoteTtsDelegate)) {
                return (IRemoteTtsDelegate) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1598968902) {
                parcel2.writeString(IRemoteTtsDelegate.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    parcel.enforceInterface(IRemoteTtsDelegate.DESCRIPTOR);
                    int iInitialize = initialize(IObjectWrapper.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt() != 0 ? TtsOptionsParcel.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    parcel2.writeInt(iInitialize);
                    return true;
                case 2:
                    parcel.enforceInterface(IRemoteTtsDelegate.DESCRIPTOR);
                    int modelLevel = getModelLevel();
                    parcel2.writeNoException();
                    parcel2.writeInt(modelLevel);
                    return true;
                case 3:
                    parcel.enforceInterface(IRemoteTtsDelegate.DESCRIPTOR);
                    updateConfig(parcel.readInt() != 0 ? TtsOptionsParcel.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    parcel.enforceInterface(IRemoteTtsDelegate.DESCRIPTOR);
                    addCallback(IRemoteTtsCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 5:
                    parcel.enforceInterface(IRemoteTtsDelegate.DESCRIPTOR);
                    startSynthesis(parcel.readInt() != 0 ? TtsTextParcel.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    parcel.enforceInterface(IRemoteTtsDelegate.DESCRIPTOR);
                    stopSynthesis(parcel.readInt() != 0 ? TtsCancelParcel.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    parcel.enforceInterface(IRemoteTtsDelegate.DESCRIPTOR);
                    int iDestroy = destroy();
                    parcel2.writeNoException();
                    parcel2.writeInt(iDestroy);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IRemoteTtsDelegate {
            public static IRemoteTtsDelegate sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteTtsDelegate.DESCRIPTOR;
            }

            @Override // com.huawei.hms.mlkit.tts.common.IRemoteTtsDelegate
            public int initialize(IObjectWrapper iObjectWrapper, TtsOptionsParcel ttsOptionsParcel) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteTtsDelegate.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iObjectWrapper != null ? iObjectWrapper.asBinder() : null);
                    if (ttsOptionsParcel != null) {
                        parcelObtain.writeInt(1);
                        ttsOptionsParcel.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().initialize(iObjectWrapper, ttsOptionsParcel);
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.mlkit.tts.common.IRemoteTtsDelegate
            public int getModelLevel() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteTtsDelegate.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getModelLevel();
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.mlkit.tts.common.IRemoteTtsDelegate
            public void updateConfig(TtsOptionsParcel ttsOptionsParcel) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteTtsDelegate.DESCRIPTOR);
                    if (ttsOptionsParcel != null) {
                        parcelObtain.writeInt(1);
                        ttsOptionsParcel.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateConfig(ttsOptionsParcel);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.mlkit.tts.common.IRemoteTtsDelegate
            public void addCallback(IRemoteTtsCallback iRemoteTtsCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteTtsDelegate.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iRemoteTtsCallback != null ? iRemoteTtsCallback.asBinder() : null);
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addCallback(iRemoteTtsCallback);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.mlkit.tts.common.IRemoteTtsDelegate
            public void startSynthesis(TtsTextParcel ttsTextParcel) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteTtsDelegate.DESCRIPTOR);
                    if (ttsTextParcel != null) {
                        parcelObtain.writeInt(1);
                        ttsTextParcel.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(5, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startSynthesis(ttsTextParcel);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.mlkit.tts.common.IRemoteTtsDelegate
            public void stopSynthesis(TtsCancelParcel ttsCancelParcel) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteTtsDelegate.DESCRIPTOR);
                    if (ttsCancelParcel != null) {
                        parcelObtain.writeInt(1);
                        ttsCancelParcel.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(6, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopSynthesis(ttsCancelParcel);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.mlkit.tts.common.IRemoteTtsDelegate
            public int destroy() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteTtsDelegate.DESCRIPTOR);
                    if (!this.mRemote.transact(7, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().destroy();
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IRemoteTtsDelegate iRemoteTtsDelegate) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iRemoteTtsDelegate == null) {
                return false;
            }
            Proxy.sDefaultImpl = iRemoteTtsDelegate;
            return true;
        }

        public static IRemoteTtsDelegate getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
