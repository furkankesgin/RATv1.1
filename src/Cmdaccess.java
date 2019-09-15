import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JOptionPane;

public class Cmdaccess {
	void cmd(String command) {
		try {
		  ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", command);
  		builder.redirectErrorStream(true);
  		Process p = builder.start();
  		BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
  		String line;
  		while(true) {
  			line = r.readLine();
  			if(line == null) {break;}
  			JOptionPane.showMessageDialog(null, line);
  		}
          
	}catch(IOException io) {
		JOptionPane.showMessageDialog(null,"io");
	}

}
}
