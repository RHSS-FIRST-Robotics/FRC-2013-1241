/*
 * Common unit conversions 
 */
package theory6.utilities;

/**
 *
 * @author Sagar
 */
public class UnitConversion {
    
    double MetersToInches(double meters)
    {
    return meters * 100.0 / 2.54;
    }

    double InchesToMeters(double inches)
    {
    return inches * 2.54 / 100.0;
    }

    double FeetToMeters(double feet)
    {
        return InchesToMeters(12 * feet);
    }

    double MetersToFeet(double meters)
    {
    return MetersToInches(meters) / 12.0;
    }

    double PoundsToKilograms(double pounds)
    {
        return pounds / 2.20462262;
    }

    double DegreesToRadians(double degrees)
    {
        return degrees * Math.PI / 180;
    }

    double RadiansToDegrees(double radians)
    {
        return radians * 180 / Math.PI;
    }
    
}
