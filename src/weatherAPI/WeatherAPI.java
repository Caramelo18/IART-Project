package weatherAPI;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;

/**
 * Created by joliveira on 20/05/2017.
 */
public class WeatherAPI {

    private String weatherCity;

    private OpenWeatherMap.Units units;

    private OpenWeatherMap owm;

    private float temperature;
    private float humidity;
    private float windSpeed;
    private int time;

    public WeatherAPI(boolean isMetric) throws IOException {
        this.units =  (isMetric) ? OpenWeatherMap.Units.METRIC
                                 : OpenWeatherMap.Units.IMPERIAL;
        String owmApiKey = "12157ac458c5e1cfcd8d5dbd1e368120";

        this.owm = new OpenWeatherMap(this.units, owmApiKey);
    }

    public void refreshValues(){
        try {
            CurrentWeather tempWeather = this.owm.currentWeatherByCityName(this.weatherCity);

            CurrentWeather.Main tempMainWeather = tempWeather.getMainInstance();
            CurrentWeather.Wind tempWindWeather = tempWeather.getWindInstance();

            this.temperature = tempMainWeather.getTemperature();
            this.humidity = tempMainWeather.getHumidity();

            this.windSpeed = tempWindWeather.getWindSpeed();

            //convert from m/s to km/h
            this.windSpeed *= 3.6;


        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public float getTemperature() {
        return temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWeatherCity(String weatherCity) {
        this.weatherCity = weatherCity;
    }
}
