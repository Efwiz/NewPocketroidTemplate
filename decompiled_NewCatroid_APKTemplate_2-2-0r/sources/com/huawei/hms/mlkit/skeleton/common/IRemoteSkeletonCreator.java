package com.huawei.hms.mlkit.skeleton.common;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes10.dex */
public interface IRemoteSkeletonCreator extends IInterface {
    public static final String DESCRIPTOR = "com.huawei.hms.mlkit.skeleton.common.IRemoteSkeletonCreator";

    public static class Default implements IRemoteSkeletonCreator {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.huawei.hms.mlkit.skeleton.common.IRemoteSkeletonCreator
        public IRemoteSkeletonDelegate newRemoteSkeletonDelegate() throws RemoteException {
            return null;
        }
    }

    IRemoteSkeletonDelegate newRemoteSkeletonDelegate() throws RemoteException;

    public static abstract class Stub extends Binder implements IRemoteSkeletonCreator {
        static final int TRANSACTION_newRemoteSkeletonDelegate = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IRemoteSkeletonCreator.DESCRIPTOR);
        }

        public static IRemoteSkeletonCreator asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IRemoteSkeletonCreator.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRemoteSkeletonCreator)) {
                return (IRemoteSkeletonCreator) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1598968902) {
                parcel2.writeString(IRemoteSkeletonCreator.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                parcel.enforceInterface(IRemoteSkeletonCreator.DESCRIPTOR);
                IRemoteSkeletonDelegate iRemoteSkeletonDelegateNewRemoteSkeletonDelegate = newRemoteSkeletonDelegate();
                parcel2.writeNoException();
                parcel2.writeStrongBinder(iRemoteSkeletonDelegateNewRemoteSkeletonDelegate != null ? iRemoteSkeletonDelegateNewRemoteSkeletonDelegate.asBinder() : null);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IRemoteSkeletonCreator {
            public static IRemoteSkeletonCreator sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteSkeletonCreator.DESCRIPTOR;
            }

            @Override // com.huawei.hms.mlkit.skeleton.common.IRemoteSkeletonCreator
            public IRemoteSkeletonDelegate newRemoteSkeletonDelegate() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteSkeletonCreator.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().newRemoteSkeletonDelegate();
                    }
                    parcelObtain2.readException();
                    return IRemoteSkeletonDelegate.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IRemoteSkeletonCreator iRemoteSkeletonCreator) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iRemoteSkeletonCreator == null) {
                return false;
            }
            Proxy.sDefaultImpl = iRemoteSkeletonCreator;
            return true;
        }

        public static IRemoteSkeletonCreator getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
