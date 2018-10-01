package com.outreach.interviews.map.builder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.outreach.interviews.map.enums.MapModes;
import com.outreach.interviews.map.enums.MapOperations;
import com.outreach.interviews.map.enums.MapRegions;

public class MapGeoCodes
{
    public static class GeoCodeBuilder {

        private String origin;
        private MapRegions region;
        private MapOperations operation;
        private JsonObject result;

        private final String URL = "https://maps.googleapis.com/maps/api/";
        private CloseableHttpClient httpclient = HttpClients.createDefault();
        
        /**
         * Set the location to check
         * @param origin String representing the location to check
         * @return {@link GeoCodeBuilder}
         */
        public GeoCodeBuilder setOrigin(String origin)  {
            this.origin = origin;
            return this;
        }
        
        /**
         * Set the region {@link MapRegions}
         * @param region Allows for en, es
         * @return {@link GeoCodeBuilder}
         */
        public GeoCodeBuilder setRegion(MapRegions region) {
            this.region = region;
            return this;
        }
        
        
        /**
         * Create the URL to communicate with the Google Maps API
         * @param type URL to provide to Apache HttpClient
         * @return {@link GeoCodeBuilder}
         */
        public GeoCodeBuilder setURL(MapOperations type) {
            this.operation = type;
            return this;
        }
        
        /**
         * Perform the HTTP request and retrieve the data from the HttpClient object
         * @return {@link GeoCodeBuilder}  
         * @throws UnsupportedOperationException
         * @throws IOException
         * @throws IllegalArgumentException
         */
        public GeoCodeBuilder build() throws UnsupportedOperationException, IOException, IllegalArgumentException {
            String requestURL = this.getURL()   + "&address=" + this.getOrigin()
                                                + "&key=" + this.getAPIKey();

            
            HttpGet httpGet = new HttpGet(requestURL);
            
            CloseableHttpResponse response = httpclient.execute(httpGet);
            
            try {
                HttpEntity entity = response.getEntity();
                String result = IOUtils.toString(entity.getContent(), "UTF-8");
                this.result = new JsonParser().parse(result).getAsJsonObject();
            }
            finally {
                response.close();
            }
            
            return this;
        }
        
        /**
         * Retrieve the geocoordinates
         * @return List of String containing latitude and longtitude
         */
        public List<String> getCoordinates() {
            if(this.operation.equals(MapOperations.geocode)) {
                List<String> list = new ArrayList<String>();
                JsonArray steps = this.result.get("results").getAsJsonArray().get(2).getAsJsonObject()
                                    .get("geometry").getAsJsonObject()
                                    .get("location").getAsJsonArray();
                

                list.add(step.get("lat").getAsDouble());
                list.add(step.get("lng").getAsDouble());

                return list;
            } else {
                throw new IllegalArgumentException("Does not support " + MapOperations.directions.name());
            }
        }
        

        //*************************For Internal Use Only***********************************//
        private final String getURL() {
            return this.URL + this.operation.name() + "/json?";
        }

        private final String getAPIKey() {
            return "AIzaSyAW0D4KOwyWK51x-Awks59RhQKuX9X3Fu4";
        }
        
        private final String getOrigin() {
            if(this.origin == null)
                throw new IllegalArgumentException("Origin cannot be empty");
            
            return this.origin;
        }
        
        private final String getRegion() {
            if(this.destination == null)
                throw new IllegalArgumentException("Region cannot be empty");
            
            return this.region.name();
        }

    }
}
