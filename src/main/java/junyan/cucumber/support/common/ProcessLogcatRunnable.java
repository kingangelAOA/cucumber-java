package junyan.cucumber.support.common;

import org.apache.log4j.Logger;

import java.io.*;

/**
 * Created by kingangeltot on 15/10/27.
 */
public class ProcessLogcatRunnable extends Common implements Runnable {
    private Process p;
    private BufferedReader br;
    private String filePath;

    private static Logger logger;

    public ProcessLogcatRunnable(Process p, String filePath) {
        this.p = p;
        this.filePath = filePath;
        System.setProperty("logPath", filePath);
        this.logger = Logger.getLogger(ProcessLogcatRunnable.class);
    }

    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(p.getInputStream());

            br = new BufferedReader(isr);

            String line;
            while ((line = br.readLine()) != null) {
                logger.info(line);
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
