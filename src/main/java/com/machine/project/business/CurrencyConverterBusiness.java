package com.machine.project.business;

import com.machine.project.domain.Currency;
import com.machine.project.utils.CurrencyEnum;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class CurrencyConverterBusiness {

    @Value("${application.currency.apikey}")
    private String apiKey;

    @Value("${application.currency.url}")
    public String url;

    @Value("${application.currency.endpoint}")
    public String endPoint;


//    public static final String apiKey = "d5d2b1e0082ac2f9fe1e724ceeee08d2";
//    public static final String url = "http://apilayer.net/api/";
//    public static final String endPoint = "live";

    static CloseableHttpClient httpClient = HttpClients.createDefault();

    public Currency getCurrency(CurrencyEnum inputCurrency, CurrencyEnum outputCurrency, Double price) {
        HttpGet get = new HttpGet(url + endPoint + "?access_key=" + apiKey);
        System.out.println(get);
        Currency currency = new Currency();
        try {
            CloseableHttpResponse response = httpClient.execute(get);
            HttpEntity entity = response.getEntity();

            // the following line converts the JSON Response to an equivalent Java Object
            JSONObject exchangeRates = new JSONObject(EntityUtils.toString(entity));

            // Parsed JSON Objects are accessed according to the JSON resonse's hierarchy, output strings are built
            Date timeStampDate = new Date((long) (exchangeRates.getLong("timestamp") * 1000));
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
            String formattedDate = dateFormat.format(timeStampDate);

            Double rate = exchangeRates.getJSONObject("quotes").getDouble(inputCurrency.name() + outputCurrency.name());
            System.out.println(rate);
            currency = new Currency(inputCurrency, outputCurrency, price, price * rate, formattedDate);
            System.out.println("__________________________________________");
            System.out.println("\n");
            System.out.println("____________________________________");
            System.out.println("price" + currency.getOutputPrice());
            System.out.println(inputCurrency.name());
            System.out.println(price);
            response.close();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return currency;
    }
}