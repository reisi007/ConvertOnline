package at.reisisoft.convert.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import at.reisisoft.convert.Job;

public interface JobQueue extends Remote {

	public Job getJob() throws RemoteException;

	public boolean addJob(Job job) throws RemoteException;

	public int count() throws RemoteException;
}
