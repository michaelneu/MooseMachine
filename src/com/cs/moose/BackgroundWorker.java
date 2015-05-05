package com.cs.moose;

import javafx.application.Platform;

/**
 * A simple replacement for the C# BackgroundWorker-class for JavaFX
 * @author minedev
 */
public abstract class BackgroundWorker {
	private Thread thread;
	private volatile boolean workerStarted;
	
	private volatile Parameters parameters;
	private volatile Object parameter;
	private volatile Exception runException;
	
	/**
	 * Initializes the BackgroundWorker
	 */
	public BackgroundWorker() {
		this.thread = new Thread() {
			public void run() {
				parameters = new Parameters(parameter, false);
				
				try {
					onDoWork(parameters);
				} catch (Exception ex) {
					runException = ex;
				}
				
				try {
					Finished finished = new Finished(runException);
					
					Platform.runLater(new Runnable() {
						
						@Override
						public void run() {
							onWorkerDone(finished);
						}
					});
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				
				workerStarted = false;
			}
		};
	}

	// Force the user to implement the behaviour
	/**
	 * The method to be called in the background
	 * @param args Arguments given in the runWorkerAsync-method
	 */
	protected abstract void onDoWork(BackgroundWorker.Parameters args);
	/**
	 * The method to be called when the worker is done. This method is back on the main thread. 
	 * @param args Information about how the worker finished
	 */
	protected abstract void onWorkerDone(BackgroundWorker.Finished args);
	/**
	 * The method to be called when the user calls `worker.reportProgress`. This method is executed on the main thread. 
	 * @param args Progress information
	 */
	protected abstract void onProgressChanged(BackgroundWorker.ProgressChanged args);
	
	/**
	 * Sets the cancelled attribute on the Arguments-object. Used for safely exiting the onDoWork-method
	 */
	public final void cancelWorkerAsync() {
		this.parameters.setCancelled();
	}

	
	/**
	 * Run the worker
	 */
	public final synchronized void runWorkerAsync() {
		runWorkerAsync(null);
	}
	/**
	 * Run the worker and pass a parameter
	 * @param parameter
	 */
	public final synchronized void runWorkerAsync(Object parameter) {
		this.parameter = parameter;
		
		if (!workerStarted && !this.thread.isAlive()) {
			workerStarted = true;
			this.thread.start();
		}
	}
	
	/**
	 * Report progress to the main thread
	 * @param progress The changed progress
	 */
	public final void reportProgress(int progress) {
		reportProgress(progress, null);
	}
	/**
	 * Report progress to the main thread and pass an user defined state
	 * @param progress The changed progress
	 * @param userState A user defined state
	 */
	public final void reportProgress(int progress, Object userState) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ProgressChanged arguments = new ProgressChanged(progress, userState);
				onProgressChanged(arguments);
			}
		});
	}
	
	/**
	 * Basically a wrapper for Thread.sleep(millis) for lazy people. 
	 * @param millis
	 */
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException ex) {
			
		}
	}
	
	/**
	 * Returns whether the worker is running or not. 
	 * @return
	 */
	public final boolean isBusy() {
		return this.workerStarted;
	}
	
	/**
	 * Supplies parameters for the onDoWork-method
	 * @author minedev
	 */
	public final class Parameters {
		private final Object parameter;
		private volatile boolean cancelled;
		
		public Parameters(Object parameter, boolean cancelled) {
			this.parameter = parameter;
			this.cancelled = cancelled;
		}

		/**
		 * Returns the parameter given in runWorkerAsync
		 * @return
		 */
		public Object getParameter() {
			return this.parameter;
		}
		
		/**
		 * returns if the worker was cancelled
		 * @return
		 */
		public boolean isCancelled() {
			return this.cancelled;
		}
		
		private void setCancelled() {
			this.cancelled = true;
		}
	}
	/**
	 * Supplies the changed and the user state
	 * @author minedev
	 */
	public final class ProgressChanged {
		private final int progress;
		private final Object userState;
		
		public ProgressChanged(int progress, Object userState) {
			this.progress = progress;
			this.userState = userState;
		}

		/**
		 * Returns the changed progress
		 * @return
		 */
		public int getProgress() {
			return this.progress;
		}

		/**
		 * Returns the user defined state
		 * @return
		 */
		public Object getUserState() {
			return this.userState;
		}
	}
	/**
	 * Supplies the exception of the onDoWork-method
	 * @author michael
	 *
	 */
	public final class Finished {
		private final Exception exception;
		
		public Finished(Exception exception) {
			this.exception = exception;
		}

		/**
		 * Returns the exception
		 * @return
		 */
		public Exception getException() {
			return this.exception;
		}
	}
}
