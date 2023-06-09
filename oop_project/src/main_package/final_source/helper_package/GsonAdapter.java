package helper_package;
import java.io.IOException;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import objects.Figure;

public class GsonAdapter extends TypeAdapter<Figure> {
    @Override
    public void write(JsonWriter out, Figure figure) throws IOException {
        out.beginObject();
        out.name("name").value(figure.getName());
        // Add more fields if needed
        out.endObject();
    }

    @Override
    public Figure read(JsonReader in) throws IOException {
        Figure figure = new Figure();
        in.beginObject();
        while (in.hasNext()) {
            String fieldName = in.nextName();
            if (fieldName.equals("name")) {
                figure.setName(in.nextString());
            }
            // Add more fields if needed
        }
        in.endObject();
        return figure;
    }
}
