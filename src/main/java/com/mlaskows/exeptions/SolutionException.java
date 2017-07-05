package com.mlaskows.exeptions;

/**
 * Created by mlaskows on 04/07/2017.
 */
public class SolutionException extends RuntimeException {

    public SolutionException(Reason reason) {
        super(reason.toString());
    }

}
