package com.mockgenerator.provider;

/**
 * User: rixonmathew
 * Date: 20/01/13
 * Time: 9:38 PM
 * This interface is used for providing a random value of the given type
 */
public interface ValueProvider<TYPE> {

    /**
     * This method will provide a random value. This is the simplest method that provides a type value
     * without check for min,max or a constant seed value
     * @return the random value;
     */
    public TYPE randomValue();


    /**
     * This overloaded method will generate a random value conforming to the input parameters passed
     * @param minLength
     * @param maxLength
     * @return the random value;
     */
    public TYPE randomValue(long minLength,long maxLength);
}
