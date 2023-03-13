package com.tp.filereader.entity;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.impl.JsonUtil;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter and mapper for {@link com.tp.filereader.entity.Data}.
 * NOTE: This class has been automatically generated from the {@link com.tp.filereader.entity.Data} original class using Vert.x codegen.
 */
public class DataConverter {


  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, Data obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "createdDate":
          if (member.getValue() instanceof Number) {
            obj.setCreatedDate(((Number)member.getValue()).longValue());
          }
          break;
        case "id":
          if (member.getValue() instanceof Number) {
            obj.setId(((Number)member.getValue()).intValue());
          }
          break;
        case "idJob":
          if (member.getValue() instanceof Number) {
            obj.setIdJob(((Number)member.getValue()).intValue());
          }
          break;
        case "name":
          if (member.getValue() instanceof String) {
            obj.setName((String)member.getValue());
          }
          break;
      }
    }
  }

  public static void toJson(Data obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(Data obj, java.util.Map<String, Object> json) {
    if (obj.getCreatedDate() != null) {
      json.put("createdDate", obj.getCreatedDate());
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getIdJob() != null) {
      json.put("idJob", obj.getIdJob());
    }
    if (obj.getName() != null) {
      json.put("name", obj.getName());
    }
  }
}
