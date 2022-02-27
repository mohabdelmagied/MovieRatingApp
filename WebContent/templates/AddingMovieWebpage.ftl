<#include "header.ftl">

<b>Welcome to our little demonstration on the MR Webapp</b><br><br>

<form method="POST" action="add_movie?action=AddingMovieWebpage">
	<fieldset id="AddingMovieWebpage">
		<legend>Required Information</legend>
		<div>
			<label>Title: </label>
			<input type="text" name="title" Required>
	    </div>

		<div>
			<label>Director: </label>
			<input type="text" name="director" Required>
	    </div>
		<div>
			<label>Main Actor</label>
			<input type="text" name="mainActors" Required>
	    </div>
	    <div>
			<label>Publishing Date: </label>
			<input type="date" name="publishing_date" Required>
	    </div>
	</fieldset>
	<button  type="submit" id="submit">Submit</button>
</form>
<#include "footer.ftl">