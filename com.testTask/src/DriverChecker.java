import java.io.File;

public class DriverChecker
{
    private static boolean[] drivers = new boolean[26];
    public char[] listDevices = new char[26];//letters drives
    int counter;//counter drives

    public DriverChecker() { counter = 0;}

    public boolean checkForDrive(String dir)
    {
        return new File(dir).exists();
    }

    public void updateDriverInfo()
    {
        for (int i = 0; i < 26; i++) {
            drivers[i] = checkForDrive((char) (i + 'A') + ":/");
        }
    }

    public  void findInfo()
    {
        for (int i = 0; i < 26; i++)
        {

            if (drivers[i])
            {
                listDevices[counter] = (char) (i + 'A');
                counter++;
            }
        }
    }

}