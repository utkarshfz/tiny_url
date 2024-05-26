package com.demo.tiny_url.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.*;

/**
 * CreateShortUrlRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-20T17:02:46.282668548Z[GMT]")

@Builder
public class CreateShortUrlRequest   {
  @JsonProperty("url")
  private String url = null;

  @JsonProperty("alias")
  private String alias = null;

  public CreateShortUrlRequest url(String url) {
    this.url = url;
    return this;
  }

  /**
   * Get url
   * @return url
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public CreateShortUrlRequest alias(String alias) {
    this.alias = alias;
    return this;
  }

  /**
   * custom url name (max length - 16)
   * @return alias
   **/
  @Schema(description = "custom url name (max length - 16)")
  
    public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateShortUrlRequest createShortUrlRequest = (CreateShortUrlRequest) o;
    return Objects.equals(this.url, createShortUrlRequest.url) &&
        Objects.equals(this.alias, createShortUrlRequest.alias);
  }

  @Override
  public int hashCode() {
    return Objects.hash(url, alias);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateShortUrlRequest {\n");
    
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
    sb.append("    alias: ").append(toIndentedString(alias)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
