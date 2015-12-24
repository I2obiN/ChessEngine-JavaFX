import java.io.OutputStream;
import java.io.PrintStream;

import javafx.scene.control.TextArea;

public class TextAreaPrintStream extends PrintStream {

    //The TextArea to which the output stream will be redirected.
    private TextArea status;

    /**
     * Method TextAreaPrintStream
     * The constructor of the class.
     * @param the TextArea to which the output stream will be redirected.
     * @param a standard output stream (needed by super method)
     **/
    public TextAreaPrintStream(TextArea area, OutputStream out) {
	super(out);
	status = area;
    }

    /**
     * Method println
     * @param the String to be output in the TextArea textArea (private
     * attribute of the class).
     * After having printed such a String, prints a new line.
     **/
    public void println(String string) {
	status.appendText(string+"\n");
    }



    /**
     * Method print
     * @param the String to be output in the TextArea textArea (private
     * attribute of the class).
     **/
    public void print(String string) {
	status.appendText(string);
    }
}
