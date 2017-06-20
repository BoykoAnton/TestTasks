import java.io.*;
import java.net.*;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class Data //class for work with open weather map API
{
    private String city,
                  country,
                  temperature,
                  humidity,
                  pressure,
                  windSpeed,
                  windDir,
                  result;

    public String getData(String urlToRead) //get data from url
    {
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        result = "";
        try {
            url = new URL(urlToRead);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result += line;
            }
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void writeToFile(String PATH_TO_PROPERTIES)
    {
        //work with write of .properties file
        FileOutputStream fileOutputStream;
        Properties prop = new Properties();

        try {
            fileOutputStream = new FileOutputStream(PATH_TO_PROPERTIES);

            prop.setProperty("city", this.getCity());
            prop.setProperty("country", this.getCountry());
            prop.setProperty("temperature", this.getTemperature());
            prop.setProperty("humidity", this.getHumidity());
            prop.setProperty("pressure", this.getPressure());
            prop.setProperty("windSpeed", this.getWindSpeed());
            prop.setProperty("windDir", this.getWindDir());

            prop.store(fileOutputStream , "");
        }
        catch (IOException e)
        {
            System.out.println("Error: file " + PATH_TO_PROPERTIES + " is not set");
            e.printStackTrace();
        }
    }

    public void findData() //find data in xml
    {
        if (result != null)
        {
            //preparing
            try
            {
                DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document doc = db.parse(new InputSource(new StringReader(result)));
                doc.getDocumentElement().normalize();

                //find city name
                NodeList tmp = doc.getElementsByTagName("city");
                Element el = (Element) tmp.item(0);
                city = el.getAttribute("name");
                country = el.getElementsByTagName("country").item(0).getTextContent();
                //System.out.println(city);

                //find temperature
                tmp = doc.getElementsByTagName("temperature");
                el = (Element) tmp.item(0);
                temperature = el.getAttribute("value") + " Celsius";
                //System.out.println(temperature);

                //find humidity
                tmp = doc.getElementsByTagName("humidity");
                el = (Element) tmp.item(0);
                humidity = el.getAttribute("value") + " %";
                //System.out.println(humidity);

                //find pressure
                tmp = doc.getElementsByTagName("pressure");
                el = (Element) tmp.item(0);
                double tmpPres = Integer.parseInt(el.getAttribute("value")) * 0.750062;//convert to mmHG
                pressure = tmpPres + " mmHg";
                //System.out.println(pressure);

                //find wind
                tmp = doc.getElementsByTagName("speed");
                el = (Element) tmp.item(0);
                windSpeed = el.getAttribute("value") + " m/s";
                tmp = doc.getElementsByTagName("direction");
                el = (Element) tmp.item(0);
                windDir = el.getAttribute("name");
                //System.out.println(wind);

            }
            catch (Exception e)
            {
                System.out.print("Error processing xml");
            }
        }

    }

    public String getAllData() //all data to string
    {
        return city + "\n" + temperature + "\n" + humidity + "\n" + pressure + "\n" + windSpeed + "\n" + windDir;
    }

    public String getCity()
    {
        return city;
    }

    public String getHumidity()
    {
        return humidity;
    }

    public String getTemperature()
    {
        return temperature;
    }

    public String getPressure()
    {
        return pressure;
    }

    public String getWindSpeed()
    {
        return windSpeed;
    }

    public String getCountry()
    {
        return country;
    }

    public String getWindDir()
    {
        return windDir;
    }

    public String getResult()
    {
        return result;
    }

    public boolean writeToFile()
    {
        String user = System.getProperty("user.name"); //for creating path to flash

        File f = new File("/media/" + user + "/");

        if (f.listFiles().length == 0)
        {
            return false;
        }

        for(File fls : f.listFiles())
        {
            this.writeToFile(fls + "/prop.properties");
        }
        return true;
    }
}