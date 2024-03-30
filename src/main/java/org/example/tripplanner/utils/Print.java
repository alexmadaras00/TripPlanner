package org.example.tripplanner.utils;

import org.example.tripplanner.services.booking.recommender.RecommenderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Print {
    public void print(Class aClass, String message){
        final Logger logger = LoggerFactory.getLogger(aClass);
        logger.info(message);
    }
}
