/**
 *@author Anton Boyko
 */

//write to flash works only on linux

public class Main //main class of the program
{
    public static void main(String[] args)
    {
        Data d = new Data();

        //MY API key 287b012b12c0f7d034138bc70ed98132
        String url = "http://api.openweathermap.org/data/2.5/weather?q=Kiev,ua&units=metric&mode=xml&appid=287b012b12c0f7d034138bc70ed98132";

        d.getData(url);//request data from server
        d.findData();//decode data  from xml

        String PATH_TO_PROPERTIES = "prop.properties";//path for local creating file

        d.writeToFile(PATH_TO_PROPERTIES);//local creating file

        if (d.writeToFile())//creating file on all usb flash cards
        {
            System.out.println("File was written on all flash cards\n");
        }
        else
        {
            System.out.println("Connect usb device!\n");
        }
    }
}
