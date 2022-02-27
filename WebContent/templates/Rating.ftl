<#include "header.ftl">

<b>Welcome to our little demonstration on the MRA Webapp</b><br><br>

<form method="Post" action=usergui?action=showRatingMovie>
	<fieldset id="rate_movie">
		<legend>Required Information</legend>
		<div>
			<label>Username: </label>
			<input type="text" name="username" Required>
	    </div>
		<div>
			<label>rate: </label>
			<input type="text" name="rate" Required>
	    </div>
		<div>
			<label>Comment: </label>
			<input type="text" name="comment" Required>
	    </div>
	    
	</fieldset>
	<button name="movie_id" type="submit" id="submit" value="${movie_id}">Submit</button>
</form>
<#include "footer.ftl">