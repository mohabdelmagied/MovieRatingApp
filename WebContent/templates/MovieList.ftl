<#include "header.ftl">

<b>Welcome to our little demonstration on the VR Webapp</b><br><br>

<form method="POST" action=usergui?action=Rating>
	<fieldset id="viewMovieList">
		<legend>Required Information</legend>
	<#if Movies??>
	<#list Movies as movie>
		<div>
			<label> Title </a></label>
			<label><button name="movie_id" value="${movie.movie_id}"> ${movie.title} </button></label>
	    </div>
		<div>
			<label>Publishing Date: </label>
			<label>${movie.publishing_date}</label>
	    </div>
		<div>
			<label> Rating: </label>
			<label>${movie.avg_rating}</label>
	    </div>
	    <div>
			<label> Director: </label>
			<label>${movie.director}</label>
	    </div>
	    <div>
			<label> Main Actor: </label>
			<label>${movie.mainActors}</label>
	    </div>
	    <#if movie.ratings??>
	    <#list movie.ratings as rating>
	    <div>
			<label> Comments: </label>
			<label>${rating.username}</label>
			<label>${rating.rate}</label>
			<label>${rating.comment}</label>
	    </div>
	    </#list>
	    </#if>
	    
	 </#list>
	    <#else >
	    	<div class="error">${message}</div>
	    </#if>
	
	
	    
	    
	</fieldset>
</form>
<#include "footer.ftl">