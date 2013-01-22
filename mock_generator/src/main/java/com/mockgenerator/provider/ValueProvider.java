package com.mockgenerator.provider;

import java.util.List;

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
     * @param minLength the min length of the random value. a value of -1 means that length is ignored
     * @param maxLength the max length of the random value. a value of -1 means that length is ignored
     * @return the random value;
     */
    public TYPE randomValue(long minLength,long maxLength);

    /**
     * This method will provide the random value formatted as a String per a given format mask.
     * @param minLength the min length of the random value. a value of -1 means that length is ignored
     * @param maxLength the max length of the random value. a value of -1 means that length is ignored
     * @param formatMask the format of the output data. e.g for Date it could be DD/MM/YYYY. For ssn's it could be
     *                   ###-##-####. for telephone numbers it could be ###-###-####. This allows the value being
     *                   returned to be formatted as per user requirements
     * @return the random value as per a given format
     */
    public String formattedRandomValue(long minLength,long maxLength,String formatMask);


    /**
     * This method allows the provider to fetch a random value from the given range that user has supplied.
     *
     * @param values list of values that user has specified
     * @return a value from within the list
     */
    public TYPE randomValueFromRange(List<TYPE> values);
}
