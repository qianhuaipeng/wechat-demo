package com.eastrobot.robotdev.service.task;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskPool {
	
	private ThreadPoolExecutor threadPool;
	
	public static TaskPool taskPool = new TaskPool();

	public TaskPool() {
		this.threadPool = new ThreadPoolExecutor(10, 30, 3, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(20), new ThreadPoolExecutor.DiscardOldestPolicy());
	}
	
	public void addTask(ITask task){
		try {
			threadPool.execute(task);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static TaskPool getInstance(){
		return taskPool;
	}
}
