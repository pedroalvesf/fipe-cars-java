package br.com.fipe.TabelaFipe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true) // ignore unknown properties;
public record Modelos(List<Data> modelos) {

}
