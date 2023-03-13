package com.tp.filereader.entity;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.impl.JsonUtil;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter and mapper for {@link com.tp.filereader.entity.Documents}.
 * NOTE: This class has been automatically generated from the {@link com.tp.filereader.entity.Documents} original class using Vert.x codegen.
 */
public class DocumentsConverter {


  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, Documents obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "categories":
          if (member.getValue() instanceof String) {
            obj.setCategories((String)member.getValue());
          }
          break;
        case "copyRightPoint":
          if (member.getValue() instanceof Number) {
            obj.setCopyRightPoint(((Number)member.getValue()).intValue());
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
        case "description":
          if (member.getValue() instanceof String) {
            obj.setDescription((String)member.getValue());
          }
          break;
        case "downDate":
          if (member.getValue() instanceof Number) {
            obj.setDownDate(((Number)member.getValue()).longValue());
          }
          break;
        case "downcount":
          if (member.getValue() instanceof Number) {
            obj.setDowncount(((Number)member.getValue()).intValue());
          }
          break;
        case "fileSize":
          if (member.getValue() instanceof String) {
            obj.setFileSize((String)member.getValue());
          }
          break;
        case "fileType":
          if (member.getValue() instanceof String) {
            obj.setFileType((String)member.getValue());
          }
          break;
        case "hashtags":
          if (member.getValue() instanceof String) {
            obj.setHashtags((String)member.getValue());
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
        case "keyword":
          if (member.getValue() instanceof String) {
            obj.setKeyword((String)member.getValue());
          }
          break;
        case "lastViewDate":
          if (member.getValue() instanceof Number) {
            obj.setLastViewDate(((Number)member.getValue()).longValue());
          }
          break;
        case "name":
          if (member.getValue() instanceof String) {
            obj.setName((String)member.getValue());
          }
          break;
        case "owner":
          if (member.getValue() instanceof String) {
            obj.setOwner((String)member.getValue());
          }
          break;
        case "pageNumber":
          if (member.getValue() instanceof Number) {
            obj.setPageNumber(((Number)member.getValue()).intValue());
          }
          break;
        case "subDescription":
          if (member.getValue() instanceof String) {
            obj.setSubDescription((String)member.getValue());
          }
          break;
        case "trendOrder":
          if (member.getValue() instanceof Number) {
            obj.setTrendOrder(((Number)member.getValue()).intValue());
          }
          break;
        case "url":
          if (member.getValue() instanceof String) {
            obj.setUrl((String)member.getValue());
          }
          break;
        case "viewcount":
          if (member.getValue() instanceof Number) {
            obj.setViewcount(((Number)member.getValue()).intValue());
          }
          break;
      }
    }
  }

  public static void toJson(Documents obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(Documents obj, java.util.Map<String, Object> json) {
    if (obj.getCategories() != null) {
      json.put("categories", obj.getCategories());
    }
    if (obj.getCopyRightPoint() != null) {
      json.put("copyRightPoint", obj.getCopyRightPoint());
    }
    if (obj.getCountry() != null) {
      json.put("country", obj.getCountry());
    }
    if (obj.getCreatedDate() != null) {
      json.put("createdDate", obj.getCreatedDate());
    }
    if (obj.getDescription() != null) {
      json.put("description", obj.getDescription());
    }
    if (obj.getDownDate() != null) {
      json.put("downDate", obj.getDownDate());
    }
    if (obj.getDowncount() != null) {
      json.put("downcount", obj.getDowncount());
    }
    if (obj.getFileSize() != null) {
      json.put("fileSize", obj.getFileSize());
    }
    if (obj.getFileType() != null) {
      json.put("fileType", obj.getFileType());
    }
    if (obj.getHashtags() != null) {
      json.put("hashtags", obj.getHashtags());
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getIsPublic() != null) {
      json.put("isPublic", obj.getIsPublic());
    }
    if (obj.getKeyword() != null) {
      json.put("keyword", obj.getKeyword());
    }
    if (obj.getLastViewDate() != null) {
      json.put("lastViewDate", obj.getLastViewDate());
    }
    if (obj.getName() != null) {
      json.put("name", obj.getName());
    }
    if (obj.getOwner() != null) {
      json.put("owner", obj.getOwner());
    }
    if (obj.getPageNumber() != null) {
      json.put("pageNumber", obj.getPageNumber());
    }
    if (obj.getSubDescription() != null) {
      json.put("subDescription", obj.getSubDescription());
    }
    if (obj.getTrendOrder() != null) {
      json.put("trendOrder", obj.getTrendOrder());
    }
    if (obj.getUrl() != null) {
      json.put("url", obj.getUrl());
    }
    if (obj.getViewcount() != null) {
      json.put("viewcount", obj.getViewcount());
    }
  }
}
