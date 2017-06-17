import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class Main //main class of the program
{
    public static void main(String[] args)
    {
        Data d = new Data();

        //MY API key 287b012b12c0f7d034138bc70ed98132
        String url = "http://api.openweathermap.org/data/2.5/weather?q=Kiev,ua&units=metric&mode=xml&appid=287b012b12c0f7d034138bc70ed98132";

        d.getData(url);//request data from server
        d.findData();//decode data  from xml

        String PATH_TO_PROPERTIES = "prop.properties";

        d.writeToFile(PATH_TO_PROPERTIES);

        try
        {
            Scanner in = new Scanner(System.in);
            System.out.println("Input path to folder for saving file");
            String tmp = in.next();

            Files.copy(Paths.get(PATH_TO_PROPERTIES), Paths.get(tmp), REPLACE_EXISTING);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
