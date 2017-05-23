package me.tre.ai.util;

import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@UtilityClass
public class LocationUtil {
    public boolean checkZip(int zip){
     //https://tools.usps.com/go/ZipLookupResultsAction!input.action?resultMode=2&companyName=&address1=&address2=&city=&state=Select&urbanCode=&postalCode=72015&zip=
        boolean found = false;
        try {
            URL url = new URL("https://www.melissadata.com/lookups/PlaceNames.asp?inData="+zip);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String str;
            while ((str = in.readLine()) != null) {
                if(found){
                    break;
                }
                if(str.contains("NOT found!")){
                    found = true;
                }
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return !found;
    }
}
