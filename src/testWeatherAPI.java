import net.aksingh.owmjapis.DailyForecast;
import org.codehaus.jettison.json.JSONException;
import java.io.IOException;
import java.net.MalformedURLException;
import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;

/**
 * Created by joliveira on 20/05/2017.
 */
public class testWeatherAPI      {
    public static void main(String[] args) throws IOException, MalformedURLException, JSONException {
        boolean isMetric = true;
        String owmApiKey = "12157ac458c5e1cfcd8d5dbd1e368120";
        String weatherCity = "Porto,PT";
        byte forecastDays = 3;

        OpenWeatherMap.Units units = (isMetric) ? OpenWeatherMap.Units.METRIC
                                                : OpenWeatherMap.Units.IMPERIAL;

        OpenWeatherMap owm = new OpenWeatherMap(units,owmApiKey);

        try {
            DailyForecast forecast
        }catch ()


    }
}
