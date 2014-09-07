package com.cyou.advertising.web.task;

import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AdSpaceConfQueue {
	private static final Log log =LogFactory.getLog(AdSpaceConfQueue.class);
	private static BlockingQueue<Integer> queue;
	
	static{
		queue = new ArrayBlockingQueue<Integer>(200);
	}
	/**
	 * insert the element intto  queue,
	 * if the queue is full, wait for space;
	 * 
	 * @param str
	 * @throws Exception 
	 */
	public static void add(Integer adSpaceConfId) throws InterruptedException {
		log.debug("queue size = "+ queue.size());
		try {
			
			queue.put(adSpaceConfId);
		} catch (InterruptedException e) {
			log.error("adSpaceId :"+adSpaceConfId+" add to queue failure");
			throw new InterruptedException("adSpaceId :"+adSpaceConfId+" add to queue failure /n" + e.getMessage());
		}
	}
	/**
	 * Retrieves and removes the head of this queue, 
	 * throw exception when the queue is empty.
	 * 
	 * @param str
	 */
	public static Integer remove() {
		log.debug("queue size = "+ queue.size());
		try {
			return queue.remove();
		} catch (NoSuchElementException e) {
			log.debug("",e);
		}
		return null;
	}
}
