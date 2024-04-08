package hw.roadroam.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hw.roadroam.models.Currency;
import hw.roadroam.repositories.CurrencyRepo;

import java.util.List;
import java.util.Optional;
import jakarta.annotation.PostConstruct;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepo currencyRepository;
    //  Epoch time from the last update
    public long lastUpdate = 0;
    //  Seconds for cache to become invalid
    public Integer cacheUpdateTime = 30;

    private static final Logger logger = LoggerFactory.getLogger(CurrencyService.class);

    @PostConstruct
    public void reloadCache() {
        logger.info("Attempting a Currency cache refresh!");

        OkHttpClient client = new OkHttpClient();
        String url = "https://v6.exchangerate-api.com/v6/0642c2c8980875e56607558a/latest/USD";

        Request request = new Request.Builder().url(url).build();


        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                logger.error("Unexpected code at cache refresh! Aborting refresh. Response: {}", response);
                throw new IOException("Unexpected code " + response);
            }

            // Parse JSON
            Gson gson = new Gson();
            Type mapType = new TypeToken<Map<String, Object>>(){}.getType();
            Map<String, Object> responseMap = gson.fromJson(response.body().string(), mapType);

            // Extract conversion_rates and time_last_update_unix
            Map<String, Double> conversionRates = (Map<String, Double>) responseMap.get("conversion_rates");
            //this.lastUpdate = ((Number) responseMap.get("time_last_update_unix")).longValue();


            Long currTime = System.currentTimeMillis() / 1000;

            if (currTime <= this.lastUpdate + this.cacheUpdateTime) {
                logger.warn("Another cache refresh was already taking place!");
                return;
            }

            this.lastUpdate = currTime;

            System.out.println("Current Cache Reload: From " + this.lastUpdate + " to " + (this.lastUpdate + this.cacheUpdateTime));

            for (Map.Entry<String, Double> set :conversionRates.entrySet()) {
                String abre = set.getKey();
                Currency curr = getCurrency(abre);

                if (curr == null) {
                    curr = new Currency();
                    curr.setAbreviation(abre);
                }

                curr.setExchangeRate(set.getValue());

                currencyRepository.save(curr);
            }

            logger.info("Successful Currency cache refresh at epoch {}! Cache will be valid until {}", this.lastUpdate, (this.lastUpdate + this.cacheUpdateTime));
        } 
        catch (IOException e) {
            logger.error("Could not reach the Currency API! Aborting refresh.");
        }

        return;
    }

    public List<Currency> listCurrencies() {
        long current_time = System.currentTimeMillis() / 1000;
        
        if (current_time > this.lastUpdate + this.cacheUpdateTime) {
            reloadCache();
        }

        return currencyRepository.findAll();
    }


    public Currency getCurrency(String abre) {
        long current_time = System.currentTimeMillis() / 1000;
        
        if (current_time > this.lastUpdate + this.cacheUpdateTime) {
            reloadCache();
        }

        Optional<Currency> currency = currencyRepository.findByAbreviation(abre);
        if (currency.isPresent()) {
            return currency.get();
        }
        else {
            return null;
        }
    }
}