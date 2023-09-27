package br.com.fipe.TabelaFipe.service;

import java.util.List;

public interface InterfaceConvertData {
  <T> T convert(String json, Class<T> classe);

  <T> List<T> convertList(String json, Class<T> classe);

}
