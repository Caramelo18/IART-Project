import net.aksingh.owmjapis.DailyForecast;
import org.codehaus.jettison.json.JSONException;
import java.io.IOException;
import java.net.MalformedURLException;
import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;

/**
 * Created by joliveira on 20/05/2017.
 */
public class weatherAPI {

    String owmApiKey = "12157ac458c5e1cfcd8d5dbd1e368120";
    boolean isMetric;
    String weatherCity;

    OpenWeatherMap.Units units;

    OpenWeatherMap owm;

    float temperature;
    float humidity;
    float windSpeed;

    public weatherAPI(String city, boolean isMetric) throws IOException {
        this.isMetric = isMetric;
        this.weatherCity = city;
        this.units =  (isMetric) ? OpenWeatherMap.Units.METRIC
                                 : OpenWeatherMap.Units.IMPERIAL;

        this.owm = new OpenWeatherMap(this.units, this.owmApiKey);
        try {
            CurrentWeather tempWeather = this.owm.currentWeatherByCityName(this.weatherCity);

            CurrentWeather.Main tempMainWeather = tempWeather.getMainInstance();
            CurrentWeather.Wind tempWindWeather = tempWeather.getWindInstance();

            this.temperature = tempMainWeather.getTemperature();
            this.humidity = tempMainWeather.getHumidity();

            this.windSpeed = tempWindWeather.getWindSpeed();


        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void refreshValues(){
        try {
            CurrentWeather tempWeather = this.owm.currentWeatherByCityName(this.weatherCity);

            CurrentWeather.Main tempMainWeather = tempWeather.getMainInstance();
            CurrentWeather.Wind tempWindWeather = tempWeather.getWindInstance();

            this.temperature = tempMainWeather.getTemperature();
            this.humidity = tempMainWeather.getHumidity();

            this.windSpeed = tempWindWeather.getWindSpeed();


        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
