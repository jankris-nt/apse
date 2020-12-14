package com.apse.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
/* import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType; */

@Entity
@Table(name = "app_service")
@Getter
@Setter
@AllArgsConstructor

@Builder
// @Document(indexName = "servdata")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Serv {	
	private  String id;
//    private @Field(type = FieldType.Text)  String app_code;
	private String app_code;
    private String service_code;
    private String name;
    private String type;
    private String sub_type;
    private Long description;
    private LocalDateTime last_modified;
    
    //@CompletionField(maxInputLength = 100)
	//private Completion suggest;
    


}
