package main_package;

import java.io.IOException;
import java.nio.charset.Charset;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class CharsetAdapter extends TypeAdapter<Charset> {

  @Override
  public Charset read(JsonReader reader) throws IOException {
    return Charset.forName(reader.nextString());
  }

  @Override
  public void write(JsonWriter writer, Charset charset) throws IOException {
    writer.value(charset.name());
  }

}
