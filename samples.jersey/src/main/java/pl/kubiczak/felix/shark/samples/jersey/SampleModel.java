package pl.kubiczak.felix.shark.samples.jersey;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

class SampleModel {

  private String name;

  private Integer number;

  SampleModel(String name, Integer number) {
    this.name = name;
    this.number = number;
  }

  String toJsonString() {
    Gson prettyPrintingGson = new GsonBuilder().setPrettyPrinting().create();
    return prettyPrintingGson.toJson(this);
  }
}
