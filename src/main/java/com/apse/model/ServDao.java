package com.apse.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.SuggestionBuilder;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//import org.elasticsearch.client.RequestOptions;

@Repository
public class ServDao {
	
	
	private final String INDEX = "appdata";
	  private final String TYPE = "apps";  
	  private RestHighLevelClient restHighLevelClient;
	  private ObjectMapper objectMapper;

	  public ServDao( ObjectMapper objectMapper, RestHighLevelClient restHighLevelClient) {
	    this.objectMapper = objectMapper;
	    this.restHighLevelClient = restHighLevelClient;
	  }
	  
	  
	  public Serv insertServ(Serv app){
		  app.setId(UUID.randomUUID().toString());
		  Map dataMap = objectMapper.convertValue(app, Map.class);
		  IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, app.getId())
		                .source(dataMap);
		  try {
		    IndexResponse response = restHighLevelClient.index(indexRequest);
		  } catch(ElasticsearchException e) {
		    e.getDetailedMessage();
		  } catch (java.io.IOException ex){
		    ex.getLocalizedMessage();
		  }
		  return app;
		}
	  
	  public Map<String, Object> getServById(String id){
		  GetRequest getRequest = new GetRequest(INDEX, TYPE, id);
		  GetResponse getResponse = null;
		  try {
		    getResponse = restHighLevelClient.get(getRequest);
		  } catch (java.io.IOException e){
		    e.getLocalizedMessage();
		  }
		  Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();
		  return sourceAsMap;
		}
	 String app = "hello";
	  
 //----------------------------------------------------------------------------------------------------------------------
	 
	 public List<Serv> getServByTitle(String title) throws IOException {

		 SearchRequest searchRequest = new SearchRequest();
	        searchRequest.indices(INDEX);
	        searchRequest.types(TYPE);

	        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

		/*
		 * MatchQueryBuilder matchQueryBuilder = QueryBuilders
		 * .matchQuery("title",title) .operator(Operator.AND);
		 * 
		 * searchSourceBuilder.query(matchQueryBuilder);
		 */
	        
	        searchSourceBuilder.query(QueryBuilders.prefixQuery("title", title)); 
	        searchSourceBuilder.from(0); 
	        searchSourceBuilder.size(5);
	        //searchSourceBuilder.sort(new FieldSortBuilder("price").order(SortOrder.DESC));
	        
	       
		
		
		  HighlightBuilder highlightBuilder = new HighlightBuilder();
		  HighlightBuilder.Field highlightTitle = new HighlightBuilder.Field("title");
		  highlightTitle.highlighterType("unified");
		  highlightBuilder.field(highlightTitle);
		  searchSourceBuilder.highlighter(highlightBuilder);
		 
	        
	        SuggestionBuilder<?> termSuggestionBuilder =
	        	    SuggestBuilders.termSuggestion("title").text(title); 
	        	SuggestBuilder suggestBuilder = new SuggestBuilder();
	        	suggestBuilder.addSuggestion("suggest_user", termSuggestionBuilder); 
	        	searchSourceBuilder.suggest(suggestBuilder);

	        
	        searchRequest.source(searchSourceBuilder);
	        
	        

	        SearchResponse searchResponse =
	        		restHighLevelClient.search(searchRequest);

	        return getSearchResult(searchResponse);
	 
		}
	  

	  
	  
	  public Map<String, Object> updateServById(String id, Serv app){
		  UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, id)
		          .fetchSource(true);    // Fetch Object after its update
		  Map<String, Object> error = new HashMap<>();
		  error.put("Error", "Unable to update app");
		  try {
		    String appJson = objectMapper.writeValueAsString(app);
		    updateRequest.doc(appJson, XContentType.JSON);
		    UpdateResponse updateResponse = restHighLevelClient.update(updateRequest);
		    Map<String, Object> sourceAsMap = updateResponse.getGetResult().sourceAsMap();
		    return sourceAsMap;
		  }catch (JsonMappingException e){
		    e.getMessage();
		  } catch (java.io.IOException e){
		    e.getLocalizedMessage();
		  }
		  return error;
		}
	  
	  
	  public void deleteServById(String id) {
		  DeleteRequest deleteRequest = new DeleteRequest(INDEX, TYPE, id);
		  try {
		    DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest);
		  } catch (java.io.IOException e){
		    e.getLocalizedMessage();
		  }
		}


	  public List<Serv> findAll() throws Exception {

	        SearchRequest searchRequest = new SearchRequest();
	        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
	        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
	        searchRequest.source(searchSourceBuilder);

	        SearchResponse searchResponse =
	        		restHighLevelClient.search(searchRequest);

	        return getSearchResult(searchResponse);


	    }

	  private List<Serv> getSearchResult(SearchResponse response) {

	        SearchHit[] searchHit = response.getHits().getHits();

	        List<Serv> app = new ArrayList<>();

	        for (SearchHit hit : searchHit){
	        	System.err.println(hit.getScore());
	        	
			
			
			  Map<String, HighlightField> highlightFields = hit.getHighlightFields();
			  HighlightField highlight = highlightFields.get("title"); Text[] fragments =
			  highlight.fragments(); String fragmentString = fragments[0].string();
			  System.err.println(fragmentString);
			 
	        	
	            
	            app
	                    .add(objectMapper
	                            .convertValue(hit
	                                    .getSourceAsMap(), Serv.class));
	        }
	        return app;
	    }
	  
	 


	public List<Serv> wildcardQuery(String query) {
		// TODO Auto-generated method stub
		return null;
	}
	  
	
}
