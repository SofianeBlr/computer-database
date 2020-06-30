function validateDates() {
	var valid = true;
	var introduced = document.getElementById("introduced").value;
	var discontinued = document.getElementById("discontinued").value;

	if (introduced != "") {
		introduced = new Date(introduced);
		if (introduced!=null){
			if (discontinued != "") {
				discontinued = new Date(discontinued);
				if (discontinued!=null){
					if (discontinued<introduced){
						valid = false;
						alert("discontinued date must be before introduced date");
					}
				} else {
					valid = false;
					alert("discontinued date invalid");
				}
			}
		} else {
			valid = false;
			alert("introduced date invalid");
		}
	} else if (discontinued != "") {
		valid = false;
		alert("introduced date must be defined to set discontinued date");
	}
	if(valid){
		alert("Computer updated");
	}
	return valid;
}


