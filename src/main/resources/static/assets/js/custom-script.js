function validatePassword() {
	var password = document.getElementById("password").value;
	var confirmPassword = document.getElementById("confirmPassword").value;
	if (password != confirmPassword) {
		alert("Passwords do not match.");
		return false;
	}
	return true;
}


/*let table1 = document.querySelector('.datatable');
let dataTable = new simpleDatatables.DataTable(table1);*/




$(function() {
	/*var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-toggle="tooltip"]'))
	var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
		return new bootstrap.Tooltip(tooltipTriggerEl);
	});
	*/

	//    $('[data-toggle="tooltip"]').tooltip();

	$('[data-bs-toggle="popover"]').popover({ trigger: 'focus' });





})