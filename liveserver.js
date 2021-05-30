/*
* liveserver.js
*
* This script will make sure all the files are upto date
* and refresh the page if it finds any file has been changed
*
* Author: satanic-devil
*
*/
const styleCount = document.styleSheets.length;
const scriptCount = document.scripts.length;
const interval = 1000;

checkAllFiles();
function checkAllFiles()  {
	let url;
	console.log("Checking files....");
	for( let i=0; i<styleCount; i++){
		url = document.styleSheets[i].href.replace("9000","9001");
		checkFile( url );
	}	

	for( let i=0; i<scriptCount; i++){
		url = document.scripts[i].src.replace("9000","9001");
		checkFile( url );
	}
	url = document.baseURI.replace("9000","9001");
	checkFile( url );
	
	setTimeout(checkAllFiles, interval);
}

async function checkFile(url){
	let res = await fetch(url);
	let result = await res.json();
	if( result.isModified == "true") document.location = document.location;
}