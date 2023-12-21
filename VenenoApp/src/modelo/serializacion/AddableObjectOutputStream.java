package modelo.serializacion;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class AddableObjectOutputStream extends ObjectOutputStream {

    public AddableObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }

    public AddableObjectOutputStream() throws IOException, SecurityException {
        super();
    }

    protected void writeStreamHeader() throws IOException {

    }

}
