package com.tp.filereader;

/**
 * Interface definition for services. 
 * Created by The HayMetric
 * Author : Vu Duy Tu
 *          duytucntt@gmail.com
 * Dec 27, 2015  
 */ 
public interface Service { 
    /** 
     * Starts the service. This method blocks until the service has completely started. 
     */ 
    void start() throws Exception;
 
 
    /** 
     * Stops the service. This method blocks until the service has completely shut down. 
     */ 
    void stop(); 
} 
