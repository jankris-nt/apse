package com.apse.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Entity
@Table(name = "app")
@Getter
@Setter
@AllArgsConstructor

@Builder
// @Document(indexName = "appdata")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class App {	
	private @Id String id;
//    private @Field(type = FieldType.Text)  String app_code;
	private String app_code;
    private String name;
    private String app_group;
    private String app_type;
    private Long description;
    private float app_cost;
    private LocalDateTime last_modified;
    
    //@CompletionField(maxInputLength = 100)
	//private Completion suggest;
    


}
