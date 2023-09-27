package br.com.fipe.TabelaFipe.main;

import br.com.fipe.TabelaFipe.model.Data;
import br.com.fipe.TabelaFipe.model.Modelos;
import br.com.fipe.TabelaFipe.model.Veiculo;
import br.com.fipe.TabelaFipe.service.ConsumeApi;
import br.com.fipe.TabelaFipe.service.ConvertData;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
  private Scanner read = new Scanner(System.in);
  private ConsumeApi consumeApi = new ConsumeApi();
  private ConvertData convert = new ConvertData();
  private final String URL_MAIN = "https://parallelum.com.br/fipe/api/v1/";
  public void showMenu() {
    var menu = """
        *** OPTIONS ***
        Carro
        Moto
        Caminhao
                
        Select one option to consult:
        """;

    System.out.println(menu);
    var option = read.nextLine();
    String address;

    if (option.toLowerCase().contains("car")) {
      address = URL_MAIN + "carros/marcas";
    } else if (option.toLowerCase().contains("mot")) {
      address = URL_MAIN + "motos/marcas";
    } else if (option.toLowerCase().contains("caminh")) {
      address = URL_MAIN + "caminhoes/marcas";
    } else {
      System.out.println("Invalid option");
      return;
    }

    var json = consumeApi.convert(address);
    System.out.println(json);

    var brands = convert.convertList(json, Data.class);
    brands.stream().sorted(Comparator.comparing(Data::codigo)).forEach(System.out::println);

    System.out.println("Select one brand code:");
    var brandChosen = read.nextLine();

    address = address + "/" + brandChosen + "/modelos";
    json = consumeApi.convert(address);
    var modelList = convert.convert(json, Modelos.class);

    modelList.modelos().stream()
        .sorted(Comparator.comparing(Data::codigo))
        .forEach(System.out::println);

    System.out.println("Choose by name: ");
    var modelChosen = read.nextLine();

    List<Data> filteredModels = modelList.modelos().stream()
        .filter(model -> model.nome().toLowerCase().contains(modelChosen.toLowerCase()))
        .toList();

    System.out.println("Filtered Model");
    filteredModels.forEach(System.out::println);

    System.out.println("Choose one model code: ");
    var modelCode = read.nextLine();

    address = address + "/" + modelCode + "/anos";
    json = consumeApi.convert(address);
    List<Data> yearList = convert.convertList(json, Data.class);
    List<Veiculo> veiculos = new ArrayList<>();

    for (int i = 0; i < yearList.size(); i++) {
      var enderecoAnos = address + "/" + yearList.get(i).codigo();
      json = consumeApi.convert(enderecoAnos);
      Veiculo veiculo = convert.convert(json, Veiculo.class);
      veiculos.add(veiculo);
    }

    System.out.println("Cars filtered by model: ");
    veiculos.forEach(System.out::println);

    System.out.println("wanna try another car?");
    var answer = read.nextLine();

    if (answer.toLowerCase().contains("y")) {
      showMenu();
    } else {
      System.out.println("Bye!");
    }
  }

}
