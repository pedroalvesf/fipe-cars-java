package br.com.fipe.TabelaFipe.service;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumeApi {

  public String convert(String address) {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder().uri(java.net.URI.create(address)).build();
      HttpResponse<String> response = null;
      try {
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
      } catch (IOException | InterruptedException e) {
        throw new RuntimeException(e);
      }

      String json = response.body();
      return json;
  }

}
