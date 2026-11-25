package com.huawei.hms.ml.common.label;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes10.dex */
public interface IRemoteLabelCreator extends IInterface {
    public static final String DESCRIPTOR = "com.huawei.hms.ml.common.label.IRemoteLabelCreator";

    public static class Default implements IRemoteLabelCreator {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.huawei.hms.ml.common.label.IRemoteLabelCreator
        public IRemoteImageLabelerDelegate newRemoteImageLabelerDelegate() throws RemoteException {
            return null;
        }
    }

    IRemoteImageLabelerDelegate newRemoteImageLabelerDelegate() throws RemoteException;

    public static abstract class Stub extends Binder implements IRemoteLabelCreator {
        static final int TRANSACTION_newRemoteImageLabelerDelegate = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IRemoteLabelCreator.DESCRIPTOR);
        }

        public static IRemoteLabelCreator asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IRemoteLabelCreator.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRemoteLabelCreator)) {
                return (IRemoteLabelCreator) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1598968902) {
                parcel2.writeString(IRemoteLabelCreator.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                parcel.enforceInterface(IRemoteLabelCreator.DESCRIPTOR);
                IRemoteImageLabelerDelegate iRemoteImageLabelerDelegateNewRemoteImageLabelerDelegate = newRemoteImageLabelerDelegate();
                parcel2.writeNoException();
                parcel2.writeStrongBinder(iRemoteImageLabelerDelegateNewRemoteImageLabelerDelegate != null ? iRemoteImageLabelerDelegateNewRemoteImageLabelerDelegate.asBinder() : null);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IRemoteLabelCreator {
            public static IRemoteLabelCreator sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteLabelCreator.DESCRIPTOR;
            }

            @Override // com.huawei.hms.ml.common.label.IRemoteLabelCreator
            public IRemoteImageLabelerDelegate newRemoteImageLabelerDelegate() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteLabelCreator.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().newRemoteImageLabelerDelegate();
                    }
                    parcelObtain2.readException();
                    return IRemoteImageLabelerDelegate.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IRemoteLabelCreator iRemoteLabelCreator) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iRemoteLabelCreator == null) {
                return false;
            }
            Proxy.sDefaultImpl = iRemoteLabelCreator;
            return true;
        }

        public static IRemoteLabelCreator getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
