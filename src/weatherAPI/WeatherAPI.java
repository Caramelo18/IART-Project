package weatherAPI;

import java.io.IOException;
import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;

public class WeatherAPI {

    private String weatherCity;
    private OpenWeatherMap owm;

    private float temperature;
    private float humidity;
    private float windSpeed;

    public WeatherAPI(boolean isMetric) throws IOException {
        OpenWeatherMap.Units units =  (isMetric) ? OpenWeatherMap.Units.METRIC
                                 : OpenWeatherMap.Units.IMPERIAL;
        String owmApiKey = "12157ac458c5e1cfcd8d5dbd1e368120";

        this.owm = new OpenWeatherMap(units, owmApiKey);
    }

    public void refreshValues() throws IOException{
        CurrentWeather tempWeather = this.owm.currentWeatherByCityName(this.weatherCity);

        CurrentWeather.Main tempMainWeather = tempWeather.getMainInstance();
        CurrentWeather.Wind tempWindWeather = tempWeather.getWindInstance();

        this.temperature = tempMainWeather.getTemperature();
        this.humidity = tempMainWeather.getHumidity();

        this.windSpeed = tempWindWeather.getWindSpeed();

        //convert from m/s to km/h
        this.windSpeed *= 3.6;
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
