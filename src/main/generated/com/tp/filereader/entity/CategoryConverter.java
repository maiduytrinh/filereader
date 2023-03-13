package com.tp.filereader.entity;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.impl.JsonUtil;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter and mapper for {@link com.tp.filereader.entity.Category}.
 * NOTE: This class has been automatically generated from the {@link com.tp.filereader.entity.Category} original class using Vert.x codegen.
 */
public class CategoryConverter {


  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, Category obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "catOrder":
          if (member.getValue() instanceof Number) {
            obj.setCatOrder(((Number)member.getValue()).intValue());
          }
          break;
        case "country":
          if (member.getValue() instanceof String) {
            obj.setCountry((String)member.getValue());
          }
          break;
        case "createdDate":
          if (member.getValue() instanceof Number) {
            obj.setCreatedDate(((Number)member.getValue()).longValue());
          }
          break;
        case "documentCount":
          if (member.getValue() instanceof Number) {
            obj.setDocumentCount(((Number)member.getValue()).intValue());
          }
          break;
        case "id":
          if (member.getValue() instanceof Number) {
            obj.setId(((Number)member.getValue()).intValue());
          }
          break;
        case "isPublic":
          if (member.getValue() instanceof String) {
            obj.setIsPublic((String)member.getValue());
          }
          break;
        case "name":
          if (member.getValue() instanceof String) {
            obj.setName((String)member.getValue());
          }
          break;
        case "rootHashtag":
          if (member.getValue() instanceof String) {
            obj.setRootHashtag((String)member.getValue());
          }
          break;
        case "thumb":
          if (member.getValue() instanceof String) {
            obj.setThumb((String)member.getValue());
          }
          break;
      }
    }
  }

  public static void toJson(Category obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(Category obj, java.util.Map<String, Object> json) {
    if (obj.getCatOrder() != null) {
      json.put("catOrder", obj.getCatOrder());
    }
    if (obj.getCountry() != null) {
      json.put("country", obj.getCountry());
    }
    if (obj.getCreatedDate() != null) {
      json.put("createdDate", obj.getCreatedDate());
    }
    if (obj.getDocumentCount() != null) {
      json.put("documentCount", obj.getDocumentCount());
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getIsPublic() != null) {
      json.put("isPublic", obj.getIsPublic());
    }
    if (obj.getName() != null) {
      json.put("name", obj.getName());
    }
    if (obj.getRootHashtag() != null) {
      json.put("rootHashtag", obj.getRootHashtag());
    }
    if (obj.getThumb() != null) {
      json.put("thumb", obj.getThumb());
    }
  }
}
