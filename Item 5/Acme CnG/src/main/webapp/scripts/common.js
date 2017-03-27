/*
 * common.js
 * 
 * Support js file.
 * 
 */

function acceptCookies() {
	var d = new Date();
	d.setTime(d.getTime() + (5 * 365 * 24 * 60 * 60 * 1000)); // Expira en 5 años
	document.cookie = "cookiesAccepted=true; expires=" + d.toUTCString() + "; path=/";
	document.getElementById("cookies-message").style.display = "none";
}
