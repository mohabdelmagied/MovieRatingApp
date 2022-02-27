<#include "header.ftl">

<b>Welcome to our little demonstration on the MRA Webapp</b><br><br>

<form method="POST" action="registergui?action=Register">
	<fieldset id="insertoffer">
		<legend>Required Information</legend>
		<div>
			<label>Username</label>
			<input type="text" name="username" Required>
	    </div>

		<div>
			<label>Age</label>
			<input type="text" name="age" min="18" Required>
	    </div>
		<div>
			<label>Email</label>
			<input type="text" name="email" Required>
	    </div>
	</fieldset>
	<button type="submit" id="register" name="register">Submit</button>
</form>
<#include "footer.ftl">